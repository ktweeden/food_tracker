package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

public class SingleMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_meal);

        Intent intent = getIntent();
        Meal selectedMeal = (Meal) intent.getSerializableExtra("meal");

        TextView name = findViewById(R.id.single_meal_name);
        name.setText(selectedMeal.getName());

        TextView date = findViewById(R.id.single_meal_date);
        date.setText(Utils.dateToString(selectedMeal.getDate()));

        TextView ratings = findViewById(R.id.single_meal_rating);
        ratings.setText(selectedMeal.getRating().name());

        Button editButton = findViewById(R.id.edit_meal_button);
        editButton.setTag(selectedMeal);

        Button deleteButton = findViewById(R.id.delete_meal_button);
        deleteButton.setTag(selectedMeal);
    }
}
