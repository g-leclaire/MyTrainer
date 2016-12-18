package com.geoff.mytrainer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Geoff on 2016-11-24.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader2.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL(CREATE_TABLE_EXERCISELOG);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Exercise.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ExerciseLog.TABLE_NAME);
        onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String CREATE_TABLE_EXERCISE =
            "CREATE TABLE " + DatabaseContract.Exercise.TABLE_NAME + " (" +
                    DatabaseContract.Exercise._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.Exercise.NAME + " TEXT," +
                    DatabaseContract.Exercise.WEIGHT + " INTEGER," +
                    DatabaseContract.Exercise.REPETITIONS + " INTEGER," +
                    DatabaseContract.Exercise.SETS + "INTEGER," +
                    DatabaseContract.Exercise.REST + "INTEGER )";

    private static final String CREATE_TABLE_EXERCISELOG =
            "CREATE TABLE " + DatabaseContract.ExerciseLog.TABLE_NAME + " (" +
                    DatabaseContract.ExerciseLog._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.ExerciseLog.EXERCISE_ID + " INTEGER," +
                    DatabaseContract.ExerciseLog.NAME + " TEXT," +
                    DatabaseContract.ExerciseLog.WEIGHT + " INTEGER," +
                    DatabaseContract.ExerciseLog.REST + "INTEGER )";

}
