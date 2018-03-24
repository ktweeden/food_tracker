package com.example.katepreston.food_tracker.Database.Contracts;

import android.provider.BaseColumns;

/**
 * Created by katepreston on 23/03/2018.
 */

public class FoodContract implements BaseColumns{
    public static final String TABLE_NAME = "foods";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_FOOD_GROUP = "food_group";
    public static final String COLUMN_NAME_DATE = "date";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodContract.TABLE_NAME + " (" +
                    FoodContract._ID + " INTEGER PRIMARY KEY," +
                    FoodContract.COLUMN_NAME_NAME + " TEXT," +
                    "FOREIGN KEY(" +FoodContract.COLUMN_NAME_FOOD_GROUP + ") REFERENCES " +
                    FoodGroupContract.TABLE_NAME+ " (" + FoodGroupContract._ID + ") " +
                    FoodContract.COLUMN_NAME_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FoodContract.TABLE_NAME;
}
