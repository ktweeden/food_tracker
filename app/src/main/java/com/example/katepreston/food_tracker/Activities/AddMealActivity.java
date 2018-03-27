package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        ArrayList<String> ratings = new ArrayList<>();
        for (Rating rating : Rating.values()) {
            ratings.add(rating.name());

            Log.d("rating", "rating: " + rating.name());
        }

        EditText mealDate = findViewById(R.id.meal_date_input);
        mealDate.setText(Utils.dateToString(Calendar.getInstance().getTime()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.rating_spinner);
        spinner.setAdapter(adapter);
    }

    public void onSubmitNewMealClick(View submitNewMeal) {
        MealDbHelper mealDbHelper = new MealDbHelper(this);

        EditText mealName = findViewById(R.id.meal_name_input);
        String name = mealName.getText().toString();

        Spinner spinner = findViewById(R.id.rating_spinner);
        Rating rating = Rating.valueOf(spinner.getSelectedItem().toString());

        EditText mealDate = findViewById(R.id.meal_date_input);
        Date date = Utils.stringToDate(mealDate.getText().toString());

        Meal meal = new Meal(date, name, rating);

        mealDbHelper.save(meal);

        Intent intent = new Intent(this, SingleMealActivity.class);

        intent.putExtra("meal", meal);
        startActivity(intent);
    }
}
