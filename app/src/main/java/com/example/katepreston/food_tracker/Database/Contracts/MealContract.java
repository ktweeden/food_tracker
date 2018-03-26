package com.example.katepreston.food_tracker.Database.Contracts;

import android.provider.BaseColumns;

/**
 * Created by katepreston on 26/03/2018.
 */

public class MealContract implements BaseColumns {
    public static final String TABLE_NAME = "meals";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_RATING = "rating";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MealContract.TABLE_NAME + " (" +
                    MealContract._ID + " INTEGER PRIMARY KEY," +
                    MealContract.COLUMN_NAME_NAME + " TEXT," +
                    MealContract.COLUMN_NAME_RATING + " TEXT," +
                    MealContract.COLUMN_NAME_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MealContract.TABLE_NAME;
}

