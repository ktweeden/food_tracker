package com.example.katepreston.food_tracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.katepreston.food_tracker.Database.Contracts.FoodContract;
import com.example.katepreston.food_tracker.Database.Contracts.FoodGroupContract;
import com.example.katepreston.food_tracker.Database.Contracts.MealContract;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.Models.FoodGroupSeeds;

import static java.lang.String.valueOf;

/**
 * Created by katepreston on 23/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 17;
    public static final String DATABASE_NAME = "FoodTracker.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("oh no", ":(");
        db.execSQL(FoodGroupContract.SQL_CREATE_ENTRIES);
        db.execSQL(FoodContract.SQL_CREATE_ENTRIES);
        db.execSQL(MealContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FoodContract.SQL_DELETE_ENTRIES);
        db.execSQL(FoodGroupContract.SQL_DELETE_ENTRIES);
        db.execSQL(MealContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
