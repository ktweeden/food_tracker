package com.example.katepreston.food_tracker.Database.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.katepreston.food_tracker.Database.Contracts.FoodContract;
import com.example.katepreston.food_tracker.Database.DbHelper;
import com.example.katepreston.food_tracker.Models.Food;

import java.util.HashMap;

/**
 * Created by katepreston on 23/03/2018.
 */

public class FoodDbHelper extends DbHelper {

    public FoodDbHelper(Context context) {
        super(context);
    }

    public void save(Food food) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(FoodContract.COLUMN_NAME_NAME, food.getName());
        values.put(FoodContract.COLUMN_NAME_FOOD_GROUP, food.getFoodGroup().toString());
        values.put(FoodContract.COLUMN_NAME_DATE, food.getDate().toString());
        food.setId(db.insert(FoodContract.TABLE_NAME, null, values));

    }

    public void update(Food food) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(FoodContract.COLUMN_NAME_NAME, food.getName());
        values.put(FoodContract.COLUMN_NAME_FOOD_GROUP, food.getFoodGroup().toString());
        values.put(FoodContract.COLUMN_NAME_DATE, food.getDate().toString());
        String whereClause = FoodContract._ID + " = ?";
        String[] whereArgs = {food.getId().toString()};

        db.update(FoodContract.TABLE_NAME, values, whereClause, whereArgs);
    }

    public void delete(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = FoodContract._ID + " = ?";
        String[] whereArgs = {food.getId().toString()};

        db.delete(FoodContract.TABLE_NAME, whereClause, whereArgs);
    }

    public Cursor findByid(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodContract._ID,
                FoodContract.COLUMN_NAME_NAME,
                FoodContract.COLUMN_NAME_FOOD_GROUP,
                FoodContract.COLUMN_NAME_DATE
        };

        String whereClause = FoodContract._ID + " = ?";
        String[] whereArgs = {id.toString()};

        return db.query(
                FoodContract.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
    }

    public Cursor findAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodContract._ID,
                FoodContract.COLUMN_NAME_NAME,
                FoodContract.COLUMN_NAME_FOOD_GROUP,
                FoodContract.COLUMN_NAME_DATE
        };

        return db.query(
                FoodContract.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }
}
