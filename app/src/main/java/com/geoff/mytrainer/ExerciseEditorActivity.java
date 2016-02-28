package com.geoff.mytrainer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // --- Initialise all the things! ---

        // Get the spinner.
        Spinner units = (Spinner) findViewById(R.id.spinner_units);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitsAdapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        units.setAdapter(unitsAdapter);

        ArrayAdapter<CharSequence> muscleAdapter = ArrayAdapter.createFromResource(this,
                R.array.muscles, android.R.layout.simple_spinner_item);
        muscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner mainMuscle = (Spinner) findViewById(R.id.spinner_mainmuscle);
        mainMuscle.setAdapter(muscleAdapter);

        Spinner secondaryMuscle = (Spinner) findViewById(R.id.spinner_secondarymuscle);
        secondaryMuscle.setAdapter(muscleAdapter);

        EditText exerciseName = (EditText) findViewById(R.id.edittext_exercise_name);
        exerciseName.setText("New exercise");

        EditText weight = (EditText) findViewById(R.id.edittext_weight);
        weight.setText("100");

        NumberPicker reps = (NumberPicker) findViewById(R.id.numberpicker_reps);
        reps.setMinValue(1);
        reps.setMaxValue(50);
        reps.setValue(8);
        reps.setEnabled(true);

        NumberPicker sets = (NumberPicker) findViewById(R.id.numberpicker_sets);
        sets.setMinValue(1);
        sets.setMaxValue(50);
        sets.setValue(3);
        sets.setEnabled(true);

        NumberPicker minutes = (NumberPicker) findViewById(R.id.numberpicker_minutes);
        minutes.setMinValue(0);
        minutes.setMaxValue(9);
        minutes.setValue(1);
        minutes.setEnabled(true);

        NumberPicker seconds = (NumberPicker) findViewById(R.id.numberpicker_seconds);
        seconds.setMinValue(0);
        seconds.setMaxValue(59);
        seconds.setValue(0);
        seconds.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                if (value < 10)
                    return "0" + value;
                else
                    return "" + value;
            }
        });
        seconds.setEnabled(true);
    }

    public void buttonSave(View view) {
        // TODO: Save data.
        finish();
    }

    public void buttonCancel(View view) {
        finish();
    }
}
