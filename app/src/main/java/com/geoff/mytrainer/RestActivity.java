package com.geoff.mytrainer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Console;
import java.util.HashSet;
import java.util.LinkedHashSet;

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

        setContentView(R.layout.activity_display_message);
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

        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);

        // Retrieve and set exercises info.
        exercises = sharedPref.getString("exercises", "error,").split(",");
        weights = sharedPref.getString("weights", "error,").split(",");
        reps = sharedPref.getString("reps", "error,").split(",");
        rests = sharedPref.getString("rests", "error,").split(",");
        sets = sharedPref.getString("sets", "error,").split(",");
        nbExercises = exercises.length;

        nextExercise();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Not used anymore.
        Bundle extras = getIntent().getExtras();
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

        // Hide skip button.
        Button skipButton = (Button) findViewById(R.id.button_skip);
        skipButton.setVisibility(View.GONE);

        // Show next button.
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setVisibility(View.VISIBLE);
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
            restTimer = new RestTimer(this, msDuration, timerText);

            if ( currentSet == Long.parseLong(sets[currentExercise]) ) {
                currentExercise++;
                currentSet = 1;
            }
            else
                currentSet++;

            switchToTimer();
            restTimer.start();
        }
        else
            finish();
    }

    private void switchToTimer() {
        // Show timer.
        TextView timerText = (TextView) findViewById(R.id.timer_text);
        timerText.setVisibility(View.VISIBLE);

        // Hide reps picker.
        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.reps_picker);
        repsPicker.setVisibility(View.GONE);

        // Show skip button.
        Button skipButton = (Button) findViewById(R.id.button_skip);
        skipButton.setVisibility(View.VISIBLE);

        // Hide next button.
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setVisibility(View.GONE);
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

    public void skipButton(View view) {
        restTimer.complete();
    }

    public void nextButton(View view) {
        nextExercise();
    }
}

