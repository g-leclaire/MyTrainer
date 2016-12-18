package com.geoff.mytrainer;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

        DatabaseHelper mDbHelper = new DatabaseHelper(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(DatabaseContract.Exercise.TABLE_NAME, null, null);
        db.delete(DatabaseContract.ExerciseLog.TABLE_NAME, null, null);

        // Create a new map of values, where column names are the keys
        ContentValues testExercise = new ContentValues();
        testExercise.put(DatabaseContract.Exercise.NAME, "Test Exercise");
        testExercise.put(DatabaseContract.Exercise.WEIGHT, 69);
        testExercise.put(DatabaseContract.Exercise.REPETITIONS, 10);
        testExercise.put(DatabaseContract.Exercise.SETS, 3);
        testExercise.put(DatabaseContract.Exercise.REST, 120);

        // Insert the new row, returning the primary key value of the new row
        for (int i = 0; i < 4; i++)
            db.insert(DatabaseContract.Exercise.TABLE_NAME, null, testExercise);

        // -------------

        db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.Exercise._ID,
                DatabaseContract.Exercise.NAME,
                DatabaseContract.Exercise.WEIGHT,
                DatabaseContract.Exercise.REPETITIONS,
                DatabaseContract.Exercise.SETS,
                DatabaseContract.Exercise.REST
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.Exercise.NAME + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.Exercise.NAME + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.Exercise.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToFirst();
        final String lExerciseName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.NAME));
        final int lExerciseWeight = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.WEIGHT));
        final int lExerciseReps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.REPETITIONS));
        final int lExerciseSets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.SETS));
        final int lExerciseRest = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.REST));

        SharedPreferences sharedPref = getSharedPreferences("Workout 1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("exercises", "Squat,Bench Press,Horizontal Row," + lExerciseName);
        editor.putString("sets", "3,3,3," + String.valueOf(lExerciseSets));
        editor.putString("reps", "8,8,8," + String.valueOf(lExerciseReps));
        editor.putString("weights", "100,90,6," + String.valueOf(lExerciseWeight));
        editor.putString("rests", "120,120,120," + String.valueOf(lExerciseRest));
        editor.putString("mainMuscles", "0,0,0,0");
        editor.putString("secondaryMuscles", "0,0,0,0");
        editor.apply();

        sharedPref = getSharedPreferences("Workout 2", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("exercises", "Deadlift,Chin-Up,Shoulder Press");
        editor.putString("sets", "3,3,3");
        editor.putString("reps", "8,8,8");
        editor.putString("weights", "90,0,45");
        editor.putString("rests", "120,120,120");
        editor.putString("mainMuscles", "0,0,0");
        editor.putString("secondaryMuscles", "0,0,0");
        editor.apply();

        sharedPref = getSharedPreferences("WorkoutInformation", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("currentWorkout", "Workout 1");
        editor.apply();
    }
}