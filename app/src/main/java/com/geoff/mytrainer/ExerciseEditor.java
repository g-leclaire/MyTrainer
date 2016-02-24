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

public class ExerciseEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the spinner.
        Spinner spinner = (Spinner) findViewById(R.id.spinner_units);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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

        TimePicker rest = (TimePicker) findViewById(R.id.timepicker_rest);
        rest.setCurrentHour(1);
        rest.setCurrentMinute(30);
        rest.setEnabled(true);
    }

    public void buttonSave(View view) {
        // TODO: Save data.
        finish();
    }

    public void buttonCancel(View view) {
        finish();
    }
}
