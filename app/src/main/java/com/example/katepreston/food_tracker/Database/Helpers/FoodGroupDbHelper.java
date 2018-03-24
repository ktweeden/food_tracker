package com.example.katepreston.food_tracker.Database.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.katepreston.food_tracker.Database.Contracts.FoodGroupContract;
import com.example.katepreston.food_tracker.Database.DbHelper;
import com.example.katepreston.food_tracker.Models.FoodGroup;

public class FoodGroupDbHelper extends DbHelper {

    public FoodGroupDbHelper(Context context) {
        super(context);
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

    public Cursor findByid(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                FoodGroupContract._ID,
                FoodGroupContract.COLUMN_NAME_NAME
        };

        String whereClause = FoodGroupContract._ID + " = ?";
        String[] whereArgs = {id.toString()};

        return db.query(
                FoodGroupContract.TABLE_NAME,
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
                FoodGroupContract._ID,
                FoodGroupContract.COLUMN_NAME_NAME
        };

        return db.query(
                FoodGroupContract.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }
}
