package com.geoff.mytrainer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
    private String[] exercises;
    private String[] weights;
    private String[] reps;
    private String[] sets;
    private String[] rests;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    public final static String EXTRA_MESSAGE = "com.geoff.myTrainer.MESSAGE";

    // TODO: Delete.
    public static final Integer[] images = { 0,0,0 };

    private List<RowItem> rowItems;

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

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("exercises", "Squat,Bench Press,Deadlift");
        editor.putString("sets", "2,2,3");
        editor.putString("reps", "8,9,8");
        editor.putString("weights", "150,120,130");
        editor.putString("rests", "30,60,90");
        editor.apply();

        // Retrieve and set exercises info.
        exercises = sharedPref.getString("exercises", "error,").split(",");
        weights = sharedPref.getString("weights", "error,").split(",");
        reps = sharedPref.getString("reps", "error,").split(",");
        rests = sharedPref.getString("rests", "error,").split(",");
        sets = sharedPref.getString("sets", "error,").split(",");

        rowItems = new ArrayList<>();
        for (int i = 0; i < exercises.length; i++) {
            RowItem item = new RowItem(images[i], exercises[i], sets[i] + " x " + reps[i] + " x " + weights[i] + " lb");
            rowItems.add(item);
        }

        ListView listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
        layout.findViewById(R.id.button_up).setVisibility(visibility);
        layout.findViewById(R.id.button_down).setVisibility(visibility);
    }

    public void buttonStart(View view) {
        Intent intent = new Intent(this, RestActivity.class);
        intent.putExtra("message", "Hello from MainActivity!");

        startActivity(intent);
    }

    public void buttonDelete(final View view) {
        // Get the list.
        final ListView list = (ListView) findViewById(R.id.list);
        // Get the list adapter.
        final CustomListViewAdapter adapter = (CustomListViewAdapter) list.getAdapter();
        // Get the item position.
        final int position = list.getPositionForView(view);
        // Get the item.
        RowItem item = adapter.getItem(position);

        // Delete dialog.
        new AlertDialog.Builder(this)
                .setTitle("Delete exercise")
                .setMessage("Delete " + item.getTitle() + " exercise?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the adapter.
                        adapter.remove(position);
                        // Hide all the items options.
                        hideItemsOptions(list);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing.
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    public void buttonEdit(View view) {
        Intent intent = new Intent(this, ExerciseEditor.class);
        intent.putExtra("message", "Hello from MainActivity!");

        startActivity(intent);
    }

    public void buttonUp(View view) {
        // Get the list.
        ListView list = (ListView) findViewById(R.id.list);
        // Get the list adapter.
        CustomListViewAdapter adapter = (CustomListViewAdapter) list.getAdapter();
        // Get the item position.
        int position = list.getPositionForView(view);

        if (position > 0) {
            // Get the item.
            RowItem item = adapter.getItem(position);
            // Remove the item from the adapter.
            adapter.remove(item);
            // Reinsert the item at the right position.
            adapter.insert(item, position - 1);
            // Hide all the items options.
            hideItemsOptions(list);
            // Get the new item view
            View newView = list.getChildAt(position - 1);
            // Show the item options.
            showItemOptions(newView);
        }
    }

    public void buttonDown(View view) {
        // Get the list.
        ListView list = (ListView) findViewById(R.id.list);
        // Get the list adapter.
        CustomListViewAdapter adapter = (CustomListViewAdapter) list.getAdapter();
        // Get the item position.
        int position = list.getPositionForView(view);

        if (position < list.getCount() - 1) {
            // Get the item.
            RowItem item = adapter.getItem(position);
            // Remove the item from the adapter.
            adapter.remove(item);
            // Reinsert the item at the right position.
            adapter.insert(item, position + 1);
            // Hide all the items options.
            hideItemsOptions(list);
            // Get the new item view
            View newView = list.getChildAt(position + 1);
            // Show the item options.
            showItemOptions(newView);
        }
    }

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
