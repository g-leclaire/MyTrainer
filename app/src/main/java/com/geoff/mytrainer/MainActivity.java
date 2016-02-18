package com.geoff.mytrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.geoff.myTrainer.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout
        setContentView(R.layout.activity_main);

        // Initialize member toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize member fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //String[] array = {"Squat", "Bench Press", "Dead Lift"};
        final UsState[] states = UsState.values();
        final ListAdapter listAdapter = createListAdapter(states);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(listAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, RestActivity.class);

        intent.putExtra("exerciseName", ((EditText) findViewById(R.id.exercise1)).getText().toString());
        intent.putExtra("exerciseReps", ((EditText) findViewById(R.id.reps1)).getText().toString());
        intent.putExtra("exerciseWeight", ((EditText) findViewById(R.id.weight1)).getText().toString());
        intent.putExtra("exerciseRest", ((EditText) findViewById(R.id.rest1)).getText().toString());

        startActivity(intent);
    }

    public enum UsState {
        Squat("3 x 8 x 150 lb"),
        BenchPress("3 x 8 x 120 lb"),
        DeadLift("3 x 8 x 130 lb");

        private String stateName;

        UsState(final String name) {
            this.stateName = name;
        }

        public String getStateName() {
            return this.stateName;
        }

        public String getAbbreviation() {
            return this.name();
        }

    }

    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";

    private List<Map<String, String>> convertToListItems(final UsState[] states) {
        final List<Map<String, String>> listItem =
                new ArrayList<Map<String, String>>(states.length);

        for (final UsState state: states) {
            final Map<String, String> listItemMap = new HashMap<String, String>();
            listItemMap.put(TEXT1, state.getStateName());
            listItemMap.put(TEXT2, state.getAbbreviation());
            listItem.add(Collections.unmodifiableMap(listItemMap));
        }
        return Collections.unmodifiableList(listItem);
    }

    private ListAdapter createListAdapter(final UsState[] states) {
        final String[] fromMapKey = new String[] {TEXT1, TEXT2};
        final int[] toLayoutId = new int[] {android.R.id.text2, android.R.id.text1};
        final List<Map<String, String>> list = convertToListItems(states);

        return new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2,
                fromMapKey, toLayoutId);
    }
}
