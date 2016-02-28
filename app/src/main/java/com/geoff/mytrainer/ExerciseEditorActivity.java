package com.geoff.mytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

public class ExerciseEditorActivity extends AppCompatActivity {

    private String exercise;
    private String weight;
    private String rep;
    private String set;
    private String rest;
    private String mainMuscle;
    private String secondaryMuscle;
    private int exerciseIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ---------------- Initialize all the things! ----------------

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

        // Retrieve exercises info.
        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);
        String[] exercises = sharedPref.getString("exercises", "error,").split(",");
        String[] weights = sharedPref.getString("weights", "error,").split(",");
        String[] reps = sharedPref.getString("reps", "error,").split(",");
        String[] rests = sharedPref.getString("rests", "error,").split(",");
        String[] sets = sharedPref.getString("sets", "error,").split(",");
        String[] mainMuscles = sharedPref.getString("mainMuscles", "error,").split(",");
        String[] secondaryMuscles = sharedPref.getString("secondaryMuscles", "error,").split(",");

        // Get the exercise index sent from the calling activity.
        Bundle extras = getIntent().getExtras();
        exerciseIndex = extras.getInt("exerciseIndex", 0);

        // Set the current exercise info.
        if (exerciseIndex < exercises.length) {
            exercise = exercises[exerciseIndex];
            weight = weights[exerciseIndex];
            rep = reps[exerciseIndex];
            rest = rests[exerciseIndex];
            set = sets[exerciseIndex];
            mainMuscle = mainMuscles[exerciseIndex];
            secondaryMuscle = secondaryMuscles[exerciseIndex];
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
        // TODO: Save data.
        // Start the exercise  editor with the exercise index as the number of exercises.
        Intent intent = new Intent(this, ExerciseListActivity.class);
        intent.putExtra("isNewExercise", true);
        startActivity(intent);
    }

    public void buttonCancel(View view) {
        Intent intent = new Intent(this, ExerciseListActivity.class);
        intent.putExtra("isNewExercise", false);
        startActivity(intent);
    }
}
