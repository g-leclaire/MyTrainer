package com.geoff.mytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestActivity extends TimerActivity{

    private RestTimer restTimer;
    private List<String> exercises;
    private List<String> weights;
    private List<String> reps;
    private List<String> rests;
    private List<String> sets;
    private List<String> mainMuscles;
    private List<String> secondaryMuscles;
    private String currentWorkout;
    private int nbExercises;
    private int currentExercise;
    private int currentSet;

    public RestActivity() {
        nbExercises = 0;
        currentExercise = 0;
        currentSet = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rest_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Not used anymore.
        Bundle extras = getIntent().getExtras();

        retrieveExercises();
        nbExercises = exercises.size();

        // Set the reps picker properties.
        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.reps_picker);
        //repsPicker.setHorizontalGravity(Gravity.CENTER);
        repsPicker.setMaxValue(20);
        repsPicker.setMinValue(0);
        repsPicker.setValue(8);
        repsPicker.setWrapSelectorWheel(false);
        //numberPicker.setOnValueChangedListener(this);


        // Create the timer object.
        this.restTimer = new RestTimer(this, (TextView) findViewById(R.id.timer_text), (ProgressBar) findViewById(R.id.progressBar));

        // Start the workout.
        nextExercise();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Not used anymore.
        Bundle extras = getIntent().getExtras();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Debug.
        System.out.println("Pause!!!");
    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        // Debug.
        System.out.println("Stop!!!");
    }

    private void retrieveExercises()
    {
        SharedPreferences sharedPref = getSharedPreferences("WorkoutInformation", Context.MODE_PRIVATE);
        if (sharedPref != null)
            currentWorkout = sharedPref.getString("currentWorkout", "error");

        sharedPref = getSharedPreferences(currentWorkout, Context.MODE_PRIVATE);
        // Retrieve and set exercises info.
        if (sharedPref != null) {
            exercises = new ArrayList<>(Arrays.asList(sharedPref.getString("exercises", "error,").split(",")));
            weights = new ArrayList<>(Arrays.asList(sharedPref.getString("weights", "error,").split(",")));
            reps = new ArrayList<>(Arrays.asList(sharedPref.getString("reps", "error,").split(",")));
            rests = new ArrayList<>(Arrays.asList(sharedPref.getString("rests", "error,").split(",")));
            sets = new ArrayList<>(Arrays.asList(sharedPref.getString("sets", "error,").split(",")));
            mainMuscles = new ArrayList<>(Arrays.asList(sharedPref.getString("mainMuscles", "error,").split(",")));
            secondaryMuscles = new ArrayList<>(Arrays.asList(sharedPref.getString("secondaryMuscles", "error,").split(",")));
        }
    }

    public void saveExercises(){
        SharedPreferences sharedPref = getSharedPreferences("WorkoutInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        if (sharedPref != null) {
            editor = sharedPref.edit();
            editor.putString("currentWorkout", currentWorkout);
            editor.apply();
        }

        sharedPref = getSharedPreferences(currentWorkout, Context.MODE_PRIVATE);
        if (sharedPref != null) {
            editor = sharedPref.edit();
            editor.putString("exercises", TextUtils.join(",", exercises));
            editor.putString("sets", TextUtils.join(",", sets));
            editor.putString("reps", TextUtils.join(",", reps));
            editor.putString("weights", TextUtils.join(",", weights));
            editor.putString("rests", TextUtils.join(",", rests));
            editor.putString("mainMuscles", TextUtils.join(",", mainMuscles));
            editor.putString("secondaryMuscles", TextUtils.join(",", secondaryMuscles));
            editor.apply();
        }
    }

    public void timerFinished() {

        switchToRepsPicker();
    }

    private void switchToRepsPicker() {
        // Hide timer.
        TextView timerText = (TextView) findViewById(R.id.timer_text);
        timerText.setVisibility(View.GONE);

        // Show reps picker.
        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.reps_picker);
        repsPicker.setVisibility(View.VISIBLE);

        // Show reps picker instructions.
        TextView repsInstructions = (TextView) findViewById(R.id.text_reps_instructions);
        repsInstructions.setVisibility(View.VISIBLE);

        // Hide skip button.
        Button skipButton = (Button) findViewById(R.id.button_skip);
        skipButton.setVisibility(View.GONE);

        // Show next button.
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setVisibility(View.VISIBLE);
    }

    private void switchToTimer() {
        // Show timer.
        TextView timerText = (TextView) findViewById(R.id.timer_text);
        timerText.setVisibility(View.VISIBLE);

        // Hide reps picker.
        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.reps_picker);
        repsPicker.setVisibility(View.GONE);

        // Hide reps picker instructions.
        TextView repsInstructions = (TextView) findViewById(R.id.text_reps_instructions);
        repsInstructions.setVisibility(View.GONE);

        // Show skip button.
        Button skipButton = (Button) findViewById(R.id.button_skip);
        skipButton.setVisibility(View.VISIBLE);

        // Hide next button.
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setVisibility(View.GONE);
    }

    private void nextExercise() {
        if(currentExercise < nbExercises)
        {
            final TextView exerciseName = (TextView) findViewById(R.id.exercise_name);
            exerciseName.setText(exercises.get(currentExercise) + " (" + currentSet + "/" + sets.get(currentExercise) + ")");

            final TextView exerciseWeight = (TextView) findViewById(R.id.exercise_weight);
            exerciseWeight.setText(weights.get(currentExercise));

            final TextView exerciseReps = (TextView) findViewById(R.id.exercise_reps);
            exerciseReps.setText(reps.get(currentExercise));

            String secsDurationRaw = rests.get(currentExercise);
            long msDuration = (secsDurationRaw != null ? 1000 * Long.parseLong(secsDurationRaw) : 0);

            final TextView timerText = (TextView) findViewById(R.id.timer_text);

            if ( currentSet == Long.parseLong(sets.get(currentExercise)) ) {
                currentExercise++;
                currentSet = 1;
            }
            else
                currentSet++;

            switchToTimer();
            restTimer.restart(msDuration);
        }
        else {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("message", "Hello from RestActivity!");

            startActivity(intent);
            finish();
        }
    }

    public void skipButton(View view) {
        restTimer.complete();
    }

    public void nextButton(View view) {
        nextExercise();
    }
}

