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
        editor.putString("sets", "2,1,2");
        editor.putString("reps", "8,5,8");
        editor.putString("weights", "150,120,130");
        editor.putString("rests", "30,60,90");
        editor.putString("mainMuscles", "11,4,9");
        editor.putString("secondaryMuscles", "7,14,7");
        editor.apply();

        sharedPref = getSharedPreferences("Workout 2", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("exercises", "Pullup,Overhead Press,Row,Crunch");
        editor.putString("sets", "1,2,1,1");
        editor.putString("reps", "5,5,3,12");
        editor.putString("weights", "120,170,115,10");
        editor.putString("rests", "45,60,60,30");
        editor.putString("mainMuscles", "0,0,0,0");
        editor.putString("secondaryMuscles", "0,0,0,0");
        editor.apply();

        sharedPref = getSharedPreferences("WorkoutInformation", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("currentWorkout", "Workout 1");
        editor.apply();
    }
}