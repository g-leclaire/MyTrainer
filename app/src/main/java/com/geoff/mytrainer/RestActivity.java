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

import java.io.Console;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class RestActivity extends AppCompatActivity{

    public RestActivity() {
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
        final TextView timerText = (TextView) findViewById(R.id.timer);

        // Testing shared preferences.
        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putInt("testKey", 42);
        //editor.putInt("test", 69);
        //editor.apply();
        //int time = sharedPref.getInt("testKey", -1);

        String[] rests = sharedPref.getString("rests", "error").split(",");
        //String secsDurationRaw = extras.getString("exerciseRest");
        String secsDurationRaw = rests[0];
        long msDuration = (secsDurationRaw != null ? 1000 * Long.parseLong(secsDurationRaw) : 0);

        // Create a new timer with the exercise rest.
        restTimer = new RestTimer(msDuration, timerText);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        restTimer.start();

        Bundle extras = getIntent().getExtras();
        final TextView exerciseName = (TextView) findViewById(R.id.exercise_name);
        final TextView exerciseWeight = (TextView) findViewById(R.id.exercise_weight);
        final TextView exerciseReps = (TextView) findViewById(R.id.exercise_reps);
        exerciseName.setText(extras.getString("exerciseName"));
        exerciseWeight.setText(extras.getString("exerciseWeight"));
        exerciseReps.setText(extras.getString("exerciseReps"));

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
        restTimer.stop();
    }

    //CountDownTimer countDownTimer;
    private RestTimer restTimer;
    private final int nbExercises = 3;
}

