package com.example.katepreston.food_tracker.Database.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.katepreston.food_tracker.Database.Contracts.FoodContract;
import com.example.katepreston.food_tracker.Database.DbHelper;
import com.example.katepreston.food_tracker.Models.Food;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public ArrayList<Food> findByid(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodContract._ID,
                FoodContract.COLUMN_NAME_NAME,
                FoodContract.COLUMN_NAME_FOOD_GROUP,
                FoodContract.COLUMN_NAME_DATE
        };

        String whereClause = FoodContract._ID + " = ?";
        String[] whereArgs = {id.toString()};

        Cursor cursor = db.query(
                FoodContract.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                FoodContract.COLUMN_NAME_DATE
        );

        return this.parseResults(cursor);

    }


    public ArrayList<Food> findAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodContract._ID,
                FoodContract.COLUMN_NAME_NAME,
                FoodContract.COLUMN_NAME_FOOD_GROUP,
                FoodContract.COLUMN_NAME_DATE
        };

        Cursor cursor = db.query(
                FoodContract.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                FoodContract.COLUMN_NAME_DATE
        );
        return this.parseResults(cursor);
    }

    private ArrayList<Food> parseResults(Cursor cursor) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Food> foodList = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                Food newFood = new Food(
                        cursor.getString(cursor.getColumnIndex(FoodContract.COLUMN_NAME_NAME)),
                        cursor.getLong(cursor.getColumnIndex(FoodContract.COLUMN_NAME_FOOD_GROUP)),
                        dateFormat.parse(cursor.getString(cursor.getColumnIndex(FoodContract.COLUMN_NAME_DATE)))
                );
                newFood.setId(cursor.getLong(cursor.getColumnIndexOrThrow(FoodContract._ID)));
                foodList.add(newFood);
                Log.d("Food added to list", newFood.getName());
                Log.d("Date is", newFood.getDate().toString());
            }
            catch (Exception e) {
                Log.d("Exception creating food", e.toString());
            }
        }
        cursor.close();
        return foodList;
    }
}
