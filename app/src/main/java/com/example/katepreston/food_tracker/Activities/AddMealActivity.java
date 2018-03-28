package com.example.katepreston.food_tracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddMealActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_meal, container, false);

        ArrayList<String> ratings = new ArrayList<>();
        for (Rating rating : Rating.values()) {
            ratings.add(rating.name());

            Log.d("rating", "rating: " + rating.name());
        }

        EditText mealDate = view.findViewById(R.id.meal_date_input);
        mealDate.setText(Utils.dateToString(Calendar.getInstance().getTime()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = view.findViewById(R.id.rating_spinner);
        spinner.setAdapter(adapter);
        return view;
    }

//    public void onSubmitNewMealClick(View submitNewMeal) {
//        MealDbHelper mealDbHelper = new MealDbHelper(this);
//
//        EditText mealName = findViewById(R.id.meal_name_input);
//        String name = mealName.getText().toString();
//
//        Spinner spinner = findViewById(R.id.rating_spinner);
//        Rating rating = Rating.valueOf(spinner.getSelectedItem().toString());
//
//        EditText mealDate = findViewById(R.id.meal_date_input);
//        Date date = Utils.stringToDate(mealDate.getText().toString());
//
//        Meal meal = new Meal(date, name, rating);
//
//        mealDbHelper.save(meal);
//
//        Intent intent = new Intent(this, SingleMealActivity.class);
//
//        intent.putExtra("meal", meal);
//        startActivity(intent);
//    }
}
