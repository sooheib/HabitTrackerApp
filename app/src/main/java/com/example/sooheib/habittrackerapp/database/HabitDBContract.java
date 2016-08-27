/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.example.sooheib.habittrackerapp.database;

import android.provider.BaseColumns;

/**
 * Created by sooheib on 8/27/16.
 */
public final class HabitDBContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HABITS.db";
    private static final String TABLE_HABITS = "HABITS";
    private static final String KEY_ID = "ID";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_DATE = "DATE";
    private static final String KEY_DETAILS = "DETAILS";
    private static final String KEY_COMPLETED = "COMPLETED";
    private static final String NULL = "NULL";

    /**
     * To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
     */
    public HabitDBContract() {
    }

    /**
     * Inner class that defines the table contents
     */
    public static abstract class HabitContain implements BaseColumns {
        public static final String TABLE_NAME = TABLE_HABITS;
        public static final String COLUMN_NAME_ID = KEY_ID;
        public static final String COLUMN_NAME_TITLE = KEY_TITLE;
        public static final String COLUMN_NAME_DATE = KEY_DATE;
        public static final String COLUMN_NAME_DETAILS = KEY_DETAILS;
        public static final String COLUMN_NAME_COMPLETED = KEY_COMPLETED;
        public static final String COLUMN_NAME_NULLABLE = NULL;
    }
}
