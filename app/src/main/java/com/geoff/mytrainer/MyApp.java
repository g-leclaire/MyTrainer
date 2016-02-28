package com.geoff.mytrainer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApp extends Application {

    public MyApp() {
        // this method fires only once per application start.
        // getApplicationContext returns null here


    }

    @Override
    public void onCreate() {
        super.onCreate();

        // this method fires once as well as constructor
        // but also application has context here

        SharedPreferences sharedPref = getSharedPreferences("Workout 1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("exercises", "Squat,Bench Press,Deadlift");
        editor.putString("sets", "2,2,3");
        editor.putString("reps", "8,9,8");
        editor.putString("weights", "150,120,130");
        editor.putString("rests", "30,60,90");
        editor.putString("mainMuscles", "11,4,9");
        editor.putString("secondaryMuscles", "7,14,7");
        editor.apply();

        sharedPref = getSharedPreferences("WorkoutInformation", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("currentWorkout", "Workout 1");
        editor.apply();
    }
}