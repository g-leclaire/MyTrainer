package com.geoff.mytrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity{
    protected List<String> exercises;
    protected List<String> weights;
    protected List<String> reps;
    protected List<String> rests;
    protected List<String> sets;
    protected List<String> mainMuscles;
    protected List<String> secondaryMuscles;
    protected String currentWorkout;


    protected void retrieveExercises()
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

    protected void saveExercises(){
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
}
