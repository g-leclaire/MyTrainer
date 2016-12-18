package com.geoff.mytrainer;

import android.provider.BaseColumns;

/**
 * Created by Geoff on 2016-11-24.
 */
public final class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseContract() {}

    /* Inner class that defines the table contents */
    public class Exercise implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";
        public static final String REPETITIONS = "repetitions";
        public static final String SETS = "sets";
        public static final String REST = "rest";

        private String name;
        private int weight;
        private int repetitions;

    }

    public static class ExerciseLog implements BaseColumns {
        public static final String TABLE_NAME = "exerciseLog";
        public static final String EXERCISE_ID = "exerciseId";
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";
        public static final String REST = "rest";
    }
}
