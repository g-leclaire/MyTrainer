package com.geoff.mytrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText notes = (EditText) findViewById(R.id.edittext_notes);
        notes.setHint("Take notes about your workout.");
        notes.setBackgroundColor(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        EditText notes = (EditText) findViewById(R.id.edittext_notes);
        notes.clearFocus();

        SharedPreferences sharedPref = getSharedPreferences("WorkoutLog", Context.MODE_PRIVATE);
        Set<String> exercisesSet = sharedPref.getStringSet("exercises", new HashSet<String>());

        ArrayList<String> exercisesList = new ArrayList<String>(exercisesSet);
        Collections.sort(exercisesList);

        String newText = "";

        for (String element : exercisesList){
            newText += element;
            newText += '\n';
        }

        try {
            Calendar cal = Calendar.getInstance();
            final int minute    = cal.get(Calendar.MINUTE);
            final int hour      = cal.get(Calendar.HOUR);
            final int day       = cal.get(Calendar.DAY_OF_MONTH);
            final int month     = cal.get(Calendar.MONTH);
            final int year      = cal.get(Calendar.YEAR);

            File myFile = new File("/sdcard/MyTrainerLog_" + year + "-" + month + "-" + day + "_" + hour + minute + ".txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(newText);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        TextView summary = (TextView) findViewById(R.id.text_summary);
        summary.setText(newText);
    }

    public void buttonSaveNotes(View view) {
        finish();
    }
}
