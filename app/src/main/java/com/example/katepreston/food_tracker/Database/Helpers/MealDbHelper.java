package com.example.katepreston.food_tracker.Database.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.katepreston.food_tracker.Database.Contracts.MealContract;
import com.example.katepreston.food_tracker.Database.DbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;

import java.util.ArrayList;

/**
 * Created by katepreston on 26/03/2018.
 */

public class MealDbHelper extends DbHelper {
    private Context context;

    public MealDbHelper(Context context) {
        super(context);
        this.context = context;
    }

    public void save(Meal meal) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(MealContract.COLUMN_NAME_NAME, meal.getName());
        values.put(MealContract.COLUMN_NAME_DATE, Utils.dateToString(meal.getDate()));
        values.put(MealContract.COLUMN_NAME_RATING, meal.getRating().toString());
        meal.setId(db.insert(MealContract.TABLE_NAME, null, values));
    }

    public void update(Meal meal) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(MealContract.COLUMN_NAME_NAME, meal.getName());
        values.put(MealContract.COLUMN_NAME_DATE, Utils.dateToString(meal.getDate()));
        values.put(MealContract.COLUMN_NAME_RATING, meal.getRating().toString());

        String whereClause = MealContract._ID + " = ?";
        String[] whereArgs = {meal.getId().toString()};

        db.update(MealContract.TABLE_NAME, values, whereClause, whereArgs);
    }

    public void delete(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = MealContract._ID + " = ?";
        String[] whereArgs = {meal.getId().toString()};
        db.delete(MealContract.TABLE_NAME, whereClause, whereArgs);
    }

    public ArrayList<Meal> findById(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                MealContract._ID,
                MealContract.COLUMN_NAME_RATING,
                MealContract.COLUMN_NAME_NAME,
                MealContract.COLUMN_NAME_DATE
        };

        String whereClause = MealContract._ID + " = ?";
        String[] whereArgs = {id.toString()};

        Cursor cursor = db.query(
                MealContract.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return this.parseResults(cursor);
    }

    public ArrayList<Meal> findAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                MealContract._ID,
                MealContract.COLUMN_NAME_RATING,
                MealContract.COLUMN_NAME_NAME,
                MealContract.COLUMN_NAME_DATE
        };


        Cursor cursor = db.query(
                MealContract.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        return this.parseResults(cursor);
    }

    public ArrayList<Food> findAllfoods(Meal meal) {
        FoodDbHelper foodDbHelper = new FoodDbHelper(this.context);
        return foodDbHelper.findByMealid(meal.getId());
    }

    private ArrayList<Meal> parseResults(Cursor cursor) {
        ArrayList<Meal> mealList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Meal newMeal = new Meal(
                    Utils.stringToDate(cursor.getString(cursor.getColumnIndex(MealContract.COLUMN_NAME_DATE))),
                    cursor.getString(cursor.getColumnIndex(MealContract.COLUMN_NAME_NAME)),
                    Rating.valueOf(cursor.getString(cursor.getColumnIndex(MealContract.COLUMN_NAME_RATING)))
            );
            newMeal.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MealContract._ID)));
            mealList.add(newMeal);
        }
        cursor.close();
        return mealList;
    }
}
