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

    public RestActivity() {
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
        final TextView exerciseName = (TextView) findViewById(R.id.exercise_name);
        exercises = sharedPref.getString("exercises", "error").split(",");
        exerciseName.setText(exercises[0]);
        nbExercises = exercises.length;

        final TextView exerciseWeight = (TextView) findViewById(R.id.exercise_weight);
        weights = sharedPref.getString("weights", "error").split(",");
        exerciseWeight.setText(weights[0]);

        final TextView exerciseReps = (TextView) findViewById(R.id.exercise_reps);
        reps = sharedPref.getString("reps", "error").split(",");
        exerciseReps.setText(reps[0]);

        rests = sharedPref.getString("rests", "error").split(",");
        String secsDurationRaw = rests[0];
        long msDuration = (secsDurationRaw != null ? 1000 * Long.parseLong(secsDurationRaw) : 0);

        // Start the timer.
        final TextView timerText = (TextView) findViewById(R.id.timer_text);
        restTimer = new RestTimer(this, msDuration, timerText);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Not used anymore.
        Bundle extras = getIntent().getExtras();

        restTimer.start();
    }

    public void timerFinished() {
        ((TextView) findViewById(R.id.timer_text)).setText("Timer finished.");
        nextExercise();
    }

    private void nextExercise() {
        if(currentExercise < nbExercises)
        {
            //restTimer.restart(Long.parseLong(rests[currentExercise++]) * 1000);
            TextView timerText = (TextView) findViewById(R.id.timer_text);
            timerText.setVisibility(View.GONE);

            NumberPicker repsPicker = (NumberPicker) findViewById(R.id.reps_picker);
            repsPicker.setVisibility(View.VISIBLE);

            Button skipButton = (Button) findViewById(R.id.button_skip);
            skipButton.setVisibility(View.GONE);

            Button nextButton = (Button) findViewById(R.id.button_next);
            skipButton.setVisibility(View.VISIBLE);


        }
        else
            finish();
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

    public void skipTimer(View view) {
        restTimer.complete();
    }

    //CountDownTimer countDownTimer;
    private RestTimer restTimer;
    private String[] exercises;
    private String[] weights;
    private String[] reps;
    private String[] sets;
    private String[] rests;
    private int nbExercises;
    private int currentExercise;
}

