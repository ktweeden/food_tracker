package com.example.katepreston.food_tracker.Database.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.katepreston.food_tracker.Database.Contracts.FoodGroupContract;
import com.example.katepreston.food_tracker.Database.DbHelper;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.Models.FoodGroupSeeds;

import java.sql.Date;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class FoodGroupDbHelper extends DbHelper {

    public FoodGroupDbHelper(Context context) {
        super(context);
    }

    public void seed() {
        Log.e("no", "F");
        for (FoodGroupSeeds seed : FoodGroupSeeds.values()) {
            FoodGroup foodgroup = new FoodGroup(valueOf(seed));
            Log.e("food group seeds", "onCreate foodgroup: " + foodgroup.getName());
            save(foodgroup);
        }
    }

    public void save(FoodGroup foodGroup) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(FoodGroupContract.COLUMN_NAME_NAME, foodGroup.getName());

        foodGroup.setId(db.insert(FoodGroupContract.TABLE_NAME, null, values));
    }

    public void update(FoodGroup foodGroup) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(FoodGroupContract.COLUMN_NAME_NAME, foodGroup.getName());

        String whereClause = FoodGroupContract._ID + " = ?";
        String[] whereArgs = {foodGroup.getId().toString()};

        db.update(FoodGroupContract.TABLE_NAME, values, whereClause, whereArgs);
    }

    public void delete(FoodGroup foodGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = FoodGroupContract._ID + " = ?";
        String[] whereArgs = {foodGroup.getId().toString()};

        db.delete(FoodGroupContract.TABLE_NAME, whereClause, whereArgs);
    }

    public ArrayList<FoodGroup> findByid(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodGroupContract._ID,
                FoodGroupContract.COLUMN_NAME_NAME
        };

        String whereClause = FoodGroupContract._ID + " = ?";
        String[] whereArgs = {id.toString()};

        Cursor cursor = db.query(
                FoodGroupContract.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return this.parseResults(cursor);
    }

    public ArrayList<FoodGroup> findByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodGroupContract._ID,
                FoodGroupContract.COLUMN_NAME_NAME
        };

        String whereClause = FoodGroupContract.COLUMN_NAME_NAME + " = ?";
        String[] whereArgs = {name};

        Cursor cursor = db.query(
                FoodGroupContract.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return this.parseResults(cursor);
    }

    public ArrayList<FoodGroup> findAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodGroupContract._ID,
                FoodGroupContract.COLUMN_NAME_NAME
        };

        Cursor cursor = db.query(
                FoodGroupContract.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        return this.parseResults(cursor);
    }

    private ArrayList<FoodGroup> parseResults(Cursor cursor) {
        ArrayList<FoodGroup> foodGroupList = new ArrayList<>();
        while (cursor.moveToNext()) {
            FoodGroup newFoodGroup = new FoodGroup(
                    cursor.getString(cursor.getColumnIndex(FoodGroupContract.COLUMN_NAME_NAME))
            );
            newFoodGroup.setId(cursor.getLong(cursor.getColumnIndexOrThrow(FoodGroupContract._ID)));
            foodGroupList.add(newFoodGroup);
        }
        cursor.close();
        return foodGroupList;
    }
}
