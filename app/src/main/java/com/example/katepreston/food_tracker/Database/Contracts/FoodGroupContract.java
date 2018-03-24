package com.example.katepreston.food_tracker.Database.Contracts;

import android.provider.BaseColumns;

/**
 * Created by katepreston on 23/03/2018.
 */

public class FoodGroupContract implements BaseColumns{
    public static final String TABLE_NAME = "food_group";
    public static final String COLUMN_NAME_NAME = "name";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodGroupContract.TABLE_NAME + " (" +
                    FoodGroupContract._ID + " INTEGER PRIMARY KEY," +
                    FoodGroupContract.COLUMN_NAME_NAME + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FoodGroupContract.TABLE_NAME;
}
