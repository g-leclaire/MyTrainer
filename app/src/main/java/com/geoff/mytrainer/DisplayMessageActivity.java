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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    public DisplayMessageActivity() {
        countDownTimer = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve exercise info.
        Bundle extras = getIntent().getExtras();
        final TextView exerciseName = (TextView) findViewById(R.id.exercise_name);
        final TextView exerciseWeight = (TextView) findViewById(R.id.exercise_weight);
        final TextView exerciseReps = (TextView) findViewById(R.id.exercise_reps);
        exerciseName.setText(extras.getString("exerciseName"));
        exerciseWeight.setText(extras.getString("exerciseWeight"));
        exerciseReps.setText(extras.getString("exerciseReps"));

        // Timer text.
        final TextView timer = (TextView) findViewById(R.id.timer);

        // Testing shared preferences.
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("testKey", 12000);
        editor.apply();
        int time = sharedPref.getInt("testKey", 10000);

        // Create a new timer with the exercise rest.
        countDownTimer = new CountDownTimer(Long.parseLong(extras.getString("exerciseRest")) * 1000, 10) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long tenths = (millisUntilFinished - 1000*seconds) / 10;
                long minutes = seconds / 60;
                long correctedSeconds = seconds % 60; // We want seconds to be between 0 and 60.
                timer.setText(String.format("%01d", minutes) + ":" + String.format("%02d", correctedSeconds) + ":" + String.format("%02d", tenths));
            }

            public void onFinish() {
                timer.setText("Go!");
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        countDownTimer.start();
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
        if(countDownTimer != null)
            countDownTimer.cancel();
        ((TextView) findViewById(R.id.timer)).setText("Go!");
    }

    CountDownTimer countDownTimer;
}

