package com.geoff.mytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RestActivity extends TimerActivity{

    private RestTimer restTimer;
    private String[] exercises;
    private String[] weights;
    private String[] reps;
    private String[] sets;
    private String[] rests;
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

        // Set the reps picker properties.
        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.reps_picker);
        //repsPicker.setHorizontalGravity(Gravity.CENTER);
        repsPicker.setMaxValue(20);
        repsPicker.setMinValue(0);
        repsPicker.setValue(8);
        repsPicker.setWrapSelectorWheel(false);
        //numberPicker.setOnValueChangedListener(this);

        /*RelativeLayout.LayoutParams mParams;
       // mParams = (RelativeLayout.LayoutParams)
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mParams.height = mFrame.getWidth();
        mFrame.setLayoutParams(mParams);
        mFrame.postInvalidate();*/

        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);

        // Retrieve and set exercises info.
        exercises = sharedPref.getString("exercises", "error,").split(",");
        weights = sharedPref.getString("weights", "error,").split(",");
        reps = sharedPref.getString("reps", "error,").split(",");
        rests = sharedPref.getString("rests", "error,").split(",");
        sets = sharedPref.getString("sets", "error,").split(",");
        nbExercises = exercises.length;

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

    public void timerFinished() {

        //switchToRepsPicker();
        Intent intent = new Intent(this, strawberry.class);
        intent.putExtra("message", "Hello from RestActivity!");

        startActivity(intent);
        finish();
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
            exerciseName.setText(exercises[currentExercise] + " (" + currentSet + "/" + sets[currentExercise] + ")");

            final TextView exerciseWeight = (TextView) findViewById(R.id.exercise_weight);
            exerciseWeight.setText(weights[currentExercise]);

            final TextView exerciseReps = (TextView) findViewById(R.id.exercise_reps);
            exerciseReps.setText(reps[currentExercise]);

            String secsDurationRaw = rests[currentExercise];
            long msDuration = (secsDurationRaw != null ? 1000 * Long.parseLong(secsDurationRaw) : 0);

            final TextView timerText = (TextView) findViewById(R.id.timer_text);

            if ( currentSet == Long.parseLong(sets[currentExercise]) ) {
                currentExercise++;
                currentSet = 1;
            }
            else
                currentSet++;

            switchToTimer();
            //restTimer.start();
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

