package com.geoff.mytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public final static String EXTRA_MESSAGE = "com.geoff.myTrainer.MESSAGE";

    public static final String[] titles = new String[] { "Squat",
            "Bench press", "Deadlift" };

    public static final String[] descriptions = new String[] {
            "3 x 8 x 150 lb",
            "3 x 8 x 120 lb",
            "3 x 8 x 130 lb"
    };

    public static final Integer[] images = { 0,0,0 };

    ListView listView;
    List<RowItem> rowItems;

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
        /*final UsState[] states = UsState.values();
        final ListAdapter listAdapter = createListAdapter(states);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(listAdapter);*/

        rowItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("exercises", "Squat,Bench Press,Deadlift");
        editor.putString("sets", "2,2,3");
        editor.putString("reps", "8,9,8");
        editor.putString("weights", "150,120,130");
        editor.putString("rests", "30,60,90");
        editor.apply();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Show toast message.
        Toast toast = Toast.makeText(getApplicationContext(),
                "Exercise " + (position + 1) + ": " + rowItems.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        hideItemsOptions((AdapterView<?>) view.getParent());
        showItemOptions(view);
    }

    private void showItemOptions(View view){
        // Show the options of the item.
        setOptionsVisibility(view, View.VISIBLE);
    }

    private void hideItemsOptions(AdapterView<?> parent){
        // Hides all the options of the items in the AdapterView.
        for (int i = 0; i < parent.getCount(); i++)
            setOptionsVisibility(parent.getChildAt(i), View.GONE);
    }

    private void setOptionsVisibility(View view, int visibility){
        RelativeLayout layout = (RelativeLayout) view;
        layout.findViewById(R.id.button_delete).setVisibility(visibility);
        layout.findViewById(R.id.button_edit).setVisibility(visibility);
        layout.findViewById(R.id.button_move).setVisibility(visibility);
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

        intent.putExtra("message", "Hello from MainActivity!");

        startActivity(intent);
    }

    public void buttonDelete(View view) {
        // Get the list.
        ListView list = (ListView) findViewById(R.id.list);
        // Get the list adapter.
        CustomListViewAdapter adapter = (CustomListViewAdapter) list.getAdapter();
        // Get the item position.
        int position = list.getPositionForView(view);
        // Remove the item from the adapter.
        adapter.remove(position);
        // Hide all the items options.
        hideItemsOptions(list);
    }

    public void buttonMove(View view) {
    }

    public void buttonEdit(View view) {
    }
}
