package com.geoff.mytrainer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<String> exercises;
    private List<String> weights;
    private List<String> reps;
    private List<String> rests;
    private List<String> sets;
    private List<String> mainMuscles;
    private List<String> secondaryMuscles;

    public final static String EXTRA_MESSAGE = "com.geoff.myTrainer.MESSAGE";

    // TODO: Delete.
    public static final Integer[] images = { 0,0,0 };

    private List<RowItem> rowItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the exercise  editor with the exercise index as the number of exercises.
                Intent intent = new Intent(getApplicationContext(), ExerciseEditorActivity.class);
                intent.putExtra("exerciseIndex", exercises.size());
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Retrieve and set exercises info.
        retrieveExercises();
        rowItems = makeRowItems();

        ListView listView = (ListView) findViewById(R.id.list);
        final CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideItemsOptions((AdapterView<?>) view.getParent());
                showItemOptions(view);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        retrieveExercises();
        rowItems = makeRowItems();

        final CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        // TODO: Do.
        // If an exercise was saved, show message.
        /*Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("isNewExercise", false)) {
            Snackbar.make((FloatingActionButton) findViewById(R.id.fab), "Exercise saved.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            extras.putBoolean("isNewExercise", false);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        //saveExercises();
    }

    private void retrieveExercises()
    {
        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);
        // Retrieve and set exercises info.
        exercises = new ArrayList<>( Arrays.asList(sharedPref.getString("exercises", "error,").split(",")));
        weights = new ArrayList<>( Arrays.asList(sharedPref.getString("weights", "error,").split(",")));
        reps = new ArrayList<>( Arrays.asList(sharedPref.getString("reps", "error,").split(",")));
        rests = new ArrayList<>( Arrays.asList(sharedPref.getString("rests", "error,").split(",")));
        sets = new ArrayList<>( Arrays.asList(sharedPref.getString("sets", "error,").split(",")));
        mainMuscles = new ArrayList<>( Arrays.asList(sharedPref.getString("mainMuscles", "error,").split(",")));
        secondaryMuscles = new ArrayList<>( Arrays.asList(sharedPref.getString("secondaryMuscles", "error,").split(",")));
    }

    private List<RowItem> makeRowItems(){
        rowItems.clear();
        for (int i = 0; i < exercises.size(); i++) {
            RowItem item = new RowItem(images[i], exercises.get(i), sets.get(i) + " x " + reps.get(i) + " x " + weights.get(i) + " lb");
            rowItems.add(item);
        }
        return rowItems;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.strawberry, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        saveExercises();

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
        // Get the position of the exercise.
        ListView list = (ListView) findViewById(R.id.list);
        int position = list.getPositionForView(view);

        // Start the exercise  editor and send the position.
        Intent intent = new Intent(this, ExerciseEditorActivity.class);
        intent.putExtra("exerciseIndex", position);

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
            //RowItem item = adapter.getItem(position);
            // Remove the item from the adapter.
            //adapter.remove(item);
            // Reinsert the item at the right position.
            //adapter.insert(item, position - 1);


            // TODO: modify data
            String tmp;

            tmp = exercises.get(position);
            exercises.remove(position);
            exercises.add(position - 1, tmp);

            tmp = reps.get(position);
            reps.remove(position);
            reps.add(position - 1, tmp);

            tmp = sets.get(position);
            sets.remove(position);
            sets.add(position - 1, tmp);

            tmp = weights.get(position);
            weights.remove(position);
            weights.add(position - 1, tmp);

            tmp = rests.get(position);
            rests.remove(position);
            rests.add(position - 1, tmp);

            tmp = mainMuscles.get(position);
            mainMuscles.remove(position);
            mainMuscles.add(position - 1, tmp);

            tmp = secondaryMuscles.get(position);
            secondaryMuscles.remove(position);
            secondaryMuscles.add(position - 1, tmp);

            makeRowItems();
            adapter.notifyDataSetChanged();

            // Hide all the items options.
            hideItemsOptions(list);
            // Get the new item view
            View newView = list.getChildAt(position - 1);
            // Show the item options.
            showItemOptions(newView);
        }
    }

    public void saveExercises(){
        // Save the data.
        SharedPreferences sharedPref = getSharedPreferences("Exercises", Context.MODE_PRIVATE);
        if (sharedPref != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
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

    public void buttonDown(View view) {
        // Get the list.
        ListView list = (ListView) findViewById(R.id.list);
        // Get the list adapter.
        CustomListViewAdapter adapter = (CustomListViewAdapter) list.getAdapter();
        // Get the item position.
        int position = list.getPositionForView(view);

        if (position < list.getCount() - 1) {
            // Get the item.
            //RowItem item = adapter.getItem(position);
            // Remove the item from the adapter.
            //RowItem item = rowItems.get(position);
            //rowItems.remove(position);
            //rowItems.add(position + 1, item);
            //adapter.notifyDataSetChanged();

            // TODO: modify data
            String tmp;

            tmp = exercises.get(position);
            exercises.remove(position);
            exercises.add(position + 1, tmp);

            tmp = reps.get(position);
            reps.remove(position);
            reps.add(position + 1, tmp);

            tmp = sets.get(position);
            sets.remove(position);
            sets.add(position + 1, tmp);

            tmp = weights.get(position);
            weights.remove(position);
            weights.add(position + 1, tmp);

            tmp = rests.get(position);
            rests.remove(position);
            rests.add(position + 1, tmp);

            tmp = mainMuscles.get(position);
            mainMuscles.remove(position);
            mainMuscles.add(position + 1, tmp);

            tmp = secondaryMuscles.get(position);
            secondaryMuscles.remove(position);
            secondaryMuscles.add(position + 1, tmp);

            makeRowItems();
            adapter.notifyDataSetChanged();


            // Reinsert the item at the right position.
            //adapter.insert(item, position + 1);
            // Hide all the items options.
            hideItemsOptions(list);
            // Get the new item view
            View newView = list.getChildAt(position + 1);
            // Show the item options.
            showItemOptions(newView);

            //rowItems.add(new RowItem(images[0], exercises.get(0), sets.get(0) + " x " + reps.get(0) + " x " + weights.get(0) + " lb"));
            //adapter.notifyDataSetChanged();


        }

    }
}
