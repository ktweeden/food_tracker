package com.example.katepreston.food_tracker.Activities;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMealActivity extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_meal, container, false);

        getActivity().setTitle(R.string.add_meal_activity);

        ArrayList<String> ratings = new ArrayList<>();
        for (Rating rating : Rating.values()) {
            ratings.add(rating.name());

        }

        Date currentDate = Calendar.getInstance().getTime();


        EditText mealDate = view.findViewById(R.id.meal_date_input);
        mealDate.setText(Utils.dateToString(currentDate));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = view.findViewById(R.id.rating_spinner);
        spinner.setAdapter(adapter);

        Button button = view.findViewById(R.id.add_meal_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitNewMealClick(v);
            }
        });


        return view;

    }


    @Override
    public void onClick(View view) {

    }

    public void onSubmitNewMealClick(View submitNewMeal) {
        MealDbHelper mealDbHelper = new MealDbHelper(getActivity());

        EditText mealName = getActivity().findViewById(R.id.meal_name_input);
        String name = mealName.getText().toString();

        Spinner spinner = getActivity().findViewById(R.id.rating_spinner);
        Rating rating = Rating.valueOf(spinner.getSelectedItem().toString());

        EditText mealDate = getActivity().findViewById(R.id.meal_date_input);
        Date date = Utils.stringToDate(mealDate.getText().toString());

        Meal meal = new Meal(date, name, rating);

        mealDbHelper.save(meal);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("meal", meal);
        SingleMealActivity singleMealActivity = new SingleMealActivity();
        singleMealActivity.setArguments(args);
        transaction.replace(R.id.content_frame, singleMealActivity);

        transaction.addToBackStack(null);
        transaction.commit();
    }

}
