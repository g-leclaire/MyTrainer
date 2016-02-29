package com.geoff.mytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseEditorActivity extends AppCompatActivity {
    // Retrieve exercises info.
    private List<String> exercises;
    private List<String> weights;
    private List<String> reps;
    private List<String> rests;
    private List<String> sets;
    private List<String> mainMuscles;
    private List<String> secondaryMuscles;
    private String currentWorkout;

    private int exerciseIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> unitsAdapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner unitsSpinner = (Spinner) findViewById(R.id.spinner_units);
        unitsSpinner.setAdapter(unitsAdapter);

        ArrayAdapter<CharSequence> muscleAdapter = ArrayAdapter.createFromResource(this,
                R.array.muscles, android.R.layout.simple_spinner_item);
        muscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner mainMuscleSpinner = (Spinner) findViewById(R.id.spinner_mainmuscle);
        mainMuscleSpinner.setAdapter(muscleAdapter);

        Spinner secondaryMuscleSpinner = (Spinner) findViewById(R.id.spinner_secondarymuscle);
        secondaryMuscleSpinner.setAdapter(muscleAdapter);

        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.numberpicker_reps);
        repsPicker.setMinValue(1);
        repsPicker.setMaxValue(50);
        repsPicker.setEnabled(true);

        NumberPicker setsPicker = (NumberPicker) findViewById(R.id.numberpicker_sets);
        setsPicker.setMinValue(1);
        setsPicker.setMaxValue(50);
        setsPicker.setEnabled(true);

        NumberPicker minutesPicker = (NumberPicker) findViewById(R.id.numberpicker_minutes);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(9);
        minutesPicker.setEnabled(true);

        NumberPicker secondsPicker = (NumberPicker) findViewById(R.id.numberpicker_seconds);
        secondsPicker.setMinValue(0);
        secondsPicker.setMaxValue(59);
        secondsPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                if (value < 10)
                    return "0" + value;
                else
                    return "" + value;
            }
        });
        secondsPicker.setEnabled(true);


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        retrieveExercises();

        // Get the exercise index sent from the calling activity.
        Bundle extras = getIntent().getExtras();
        exerciseIndex = extras.getInt("exerciseIndex", 0);

        // Set the current exercise info.
        String exercise;
        String weight;
        String rep;
        String rest;
        String set;
        String mainMuscle;
        String secondaryMuscle;
        if (exerciseIndex < exercises.size()) {
            exercise = exercises.get(exerciseIndex);
            weight = weights.get(exerciseIndex);
            rep = reps.get(exerciseIndex);
            rest = rests.get(exerciseIndex);
            set = sets.get(exerciseIndex);
            mainMuscle = mainMuscles.get(exerciseIndex);
            secondaryMuscle = secondaryMuscles.get(exerciseIndex);
        }
        else {
            exercise = "New exercise";
            weight = "100";
            rep = "8";
            rest = "60";
            set = "3";
            mainMuscle = "0";
            secondaryMuscle = "0";
        }

        // (Re)initialize values.
        EditText exerciseNameText = (EditText) findViewById(R.id.edittext_exercise_name);
        exerciseNameText.setText(exercise);

        EditText weightText = (EditText) findViewById(R.id.edittext_weight);
        weightText.setText(weight);

        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.numberpicker_reps);
        repsPicker.setValue(Integer.parseInt(rep));

        NumberPicker setsPicker = (NumberPicker) findViewById(R.id.numberpicker_sets);
        setsPicker.setValue(Integer.parseInt(set));

        NumberPicker minutesPicker = (NumberPicker) findViewById(R.id.numberpicker_minutes);
        minutesPicker.setValue(Integer.parseInt(rest) / 60);

        NumberPicker secondsPicker = (NumberPicker) findViewById(R.id.numberpicker_seconds);
        secondsPicker.setValue(Integer.parseInt(rest) % 60);

        Spinner mainMuscleSpinner = (Spinner) findViewById(R.id.spinner_mainmuscle);
        mainMuscleSpinner.setSelection(Integer.parseInt(mainMuscle));

        Spinner secondaryMuscleSpinner = (Spinner) findViewById(R.id.spinner_secondarymuscle);
        secondaryMuscleSpinner.setSelection(Integer.parseInt(secondaryMuscle));
    }

    public void buttonSave(View view) {
        // Get all the data from the views.
        EditText exerciseNameText = (EditText) findViewById(R.id.edittext_exercise_name);
        String newExerciseName = exerciseNameText.getText().toString();

        EditText weightText = (EditText) findViewById(R.id.edittext_weight);
        String newWeight = weightText.getText().toString();

        NumberPicker repsPicker = (NumberPicker) findViewById(R.id.numberpicker_reps);
        String newRep = String.valueOf(repsPicker.getValue());

        NumberPicker setsPicker = (NumberPicker) findViewById(R.id.numberpicker_sets);
        String newSet = String.valueOf(setsPicker.getValue());

        NumberPicker minutesPicker = (NumberPicker) findViewById(R.id.numberpicker_minutes);
        NumberPicker secondsPicker = (NumberPicker) findViewById(R.id.numberpicker_seconds);
        String newRest = String.valueOf(minutesPicker.getValue() * 60 + secondsPicker.getValue());

        Spinner mainMuscleSpinner = (Spinner) findViewById(R.id.spinner_mainmuscle);
        String newMainMuscle = mainMuscleSpinner.getSelectedItem().toString();

        Spinner secondaryMuscleSpinner = (Spinner) findViewById(R.id.spinner_secondarymuscle);
        String newSecondaryMuscle = secondaryMuscleSpinner.getSelectedItem().toString();

        // Add the new data or modify it.
        if (exerciseIndex >= exercises.size()) {
            exercises.add(newExerciseName);
            weights.add(newWeight);
            reps.add(newRep);
            sets.add(newSet);
            rests.add(newRest);
            mainMuscles.add(newMainMuscle);
            secondaryMuscles.add(newSecondaryMuscle);
        }
        else {
            exercises.set(exerciseIndex, newExerciseName);
            weights.set(exerciseIndex, newWeight);
            reps.set(exerciseIndex, newRep);
            sets.set(exerciseIndex, newSet);
            rests.set(exerciseIndex, newRest);
            mainMuscles.set(exerciseIndex, newMainMuscle);
            secondaryMuscles.set(exerciseIndex, newSecondaryMuscle);
        }

        // Save the data.
        saveExercises();
        finish();
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

    public void buttonCancel(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
