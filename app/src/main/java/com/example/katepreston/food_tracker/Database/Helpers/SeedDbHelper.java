package com.example.katepreston.food_tracker.Database.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.katepreston.food_tracker.Database.DbHelper;

/**
 * Created by katepreston on 26/03/2018.
 */

public class SeedDbHelper  {
    public static void seed(Context context) {
        FoodGroupDbHelper foodGroupDbHelper = new FoodGroupDbHelper(context);
        foodGroupDbHelper.seed();
    }
}
