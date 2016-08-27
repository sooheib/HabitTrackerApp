/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.example.sooheib.habittrackerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sooheib.habittrackerapp.constants.DatabaseConstant;
import com.example.sooheib.habittrackerapp.models.HabitModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sooheib on 8/27/16.
 */
public class HabitDbHelper extends SQLiteOpenHelper {
    /**
     * CREATE TABLE HABITS ( ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,TITLE KEY NOT NULL ,DATE KEY NOT NULL ,DETAILS KEY ,COMPLETED BOOLEAN KEY NOT NULL ) ;
     */
    private static final String SQL_CREATE_ENTRIES = DatabaseConstant.CREATE_TABLE + HabitDBContract.HabitContain.TABLE_NAME
            + DatabaseConstant.OPEN_BRACKETS
            + HabitDBContract.HabitContain.COLUMN_NAME_ID + DatabaseConstant.INTEGER_PRIMARY_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + HabitDBContract.HabitContain.COLUMN_NAME_TITLE + DatabaseConstant.STRING_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + HabitDBContract.HabitContain.COLUMN_NAME_DATE + DatabaseConstant.STRING_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + HabitDBContract.HabitContain.COLUMN_NAME_DETAILS + DatabaseConstant.STRING_KEY + DatabaseConstant.COMMA_SEP
            + HabitDBContract.HabitContain.COLUMN_NAME_COMPLETED + DatabaseConstant.BOOLEAN_KEY + DatabaseConstant.NOT_NULL
            + DatabaseConstant.CLOSE_BRACKETS + DatabaseConstant.SEMICOLON;

    /**
     * DROP TABLE HABITS IF EXISTS;
     */
    /*private static final String SQL_DELETE_ENTRIES =
            DatabaseConstant.DROP_TABLE_EXISTED + HabitDBContract.HabitContain.TABLE_NAME + DatabaseConstant.SEMICOLON;*/

    /**
     * DELETE FROM HABITS;
     */
    private static final String SQL_DELETE_ALL_ENTRIES =
            DatabaseConstant.DROP_DELETE_FROM + HabitDBContract.HabitContain.TABLE_NAME + DatabaseConstant.SEMICOLON;

    /**
     * Constructor
     *
     * @param context Context
     */
    public HabitDbHelper(Context context) {
        super(context, HabitDBContract.DATABASE_NAME, null, HabitDBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        deleteDatabase();
        onCreate(sqLiteDatabase);
    }

    /**
     * Delete Database
     */
    private void deleteDatabase() {
        //sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        File file = new File(HabitDBContract.DATABASE_NAME);
        SQLiteDatabase.deleteDatabase(file);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * This function will select all record(s) of table inside of database
     *
     * @return List<HabitModel>. A list of HabitModel in the database
     */
    public List<HabitModel> selectAllHabits() {
        // Gets the data repository in read mode
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Select table columns
        String[] columns = new String[]{
                HabitDBContract.HabitContain.COLUMN_NAME_ID, HabitDBContract.HabitContain.COLUMN_NAME_TITLE,
                HabitDBContract.HabitContain.COLUMN_NAME_DATE, HabitDBContract.HabitContain.COLUMN_NAME_DETAILS,
                HabitDBContract.HabitContain.COLUMN_NAME_COMPLETED
        };

        // Get the result
        Cursor cursor = sqLiteDatabase.query(HabitDBContract.HabitContain.TABLE_NAME, columns, null, null, null, null, null);
        List<HabitModel> resultSelect = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                HabitModel habitModel = new HabitModel();
                habitModel.setmId(cursor.getInt(0));
                habitModel.setmTitle(cursor.getString(1));
                habitModel.setmDate(cursor.getString(2));
                habitModel.setmDetails(cursor.getString(3));
                habitModel.setmCompleted(cursor.getInt(4));
                resultSelect.add(habitModel);
            } while (cursor.moveToNext());
        }

        // Close connection
        if (cursor != null) cursor.close();
        sqLiteDatabase.close();

        return resultSelect;
    }

    /**
     * insertHabit
     *
     * @param habitModel HabitModel
     * @return long
     */
    public long insertHabit(HabitModel habitModel) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HabitDBContract.HabitContain.COLUMN_NAME_TITLE, habitModel.getmTitle());
        values.put(HabitDBContract.HabitContain.COLUMN_NAME_DATE, new Date().toString());
        if (habitModel.getmDetails() != null) {
            values.put(HabitDBContract.HabitContain.COLUMN_NAME_DETAILS, habitModel.getmDetails());
        }
        values.put(HabitDBContract.HabitContain.COLUMN_NAME_COMPLETED, 0);

        // Insert
        long newRowId = db.insert(
                HabitDBContract.HabitContain.TABLE_NAME,
                HabitDBContract.HabitContain.COLUMN_NAME_NULLABLE,
                values);

        // Close connection
        db.close();

        return newRowId;
    }

    /**
     * updateHabit
     *
     * @param habitModel HabitModel
     * @return int
     */
    public int updateHabit(HabitModel habitModel) {
        // Get the lock
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(HabitDBContract.HabitContain.COLUMN_NAME_DATE, new Date().toString());
        if (habitModel.getmDetails() != null) {
            values.put(HabitDBContract.HabitContain.COLUMN_NAME_DETAILS, habitModel.getmDetails());
        }
        values.put(HabitDBContract.HabitContain.COLUMN_NAME_COMPLETED, habitModel.getmCompleted());

        // Which row to update, based on the ID
        String selection = HabitDBContract.HabitContain.COLUMN_NAME_ID + DatabaseConstant.SELECTION_IS;

        // Specify arguments in placeholder order
        String[] selectionArgs = {String.valueOf(habitModel.getmId())};

        // Update
        int count = sqLiteDatabase.update(
                HabitDBContract.HabitContain.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        // Close connection
        sqLiteDatabase.close();

        return count;
    }

    /**
     * deleteHabit
     *
     * @param habitModel HabitModel
     * @return int
     */
    public int deleteHabit(HabitModel habitModel) {
        // Get the lock
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Which row to update, based on the ID
        String selection = HabitDBContract.HabitContain.COLUMN_NAME_ID + DatabaseConstant.SELECTION_IS;

        // Specify arguments in placeholder order
        String[] selectionArgs = {String.valueOf(habitModel.getmId())};

        // Delete
        int count = sqLiteDatabase.delete(
                HabitDBContract.HabitContain.TABLE_NAME,
                selection,
                selectionArgs);

        // Close connection
        sqLiteDatabase.close();

        return count;
    }

    /**
     * deleteAllHabit
     */
    public void deleteAllHabit() {
        // Get the lock
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Remove all
        sqLiteDatabase.execSQL(SQL_DELETE_ALL_ENTRIES);
        // sqLiteDatabase.delete(HabitDBContract.HabitContain.TABLE_NAME, null, null);

        // Close connection
        sqLiteDatabase.close();
    }
}
