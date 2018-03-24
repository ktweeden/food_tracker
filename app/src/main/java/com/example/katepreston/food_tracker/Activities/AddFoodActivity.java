package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.katepreston.food_tracker.Adaptors.FoodGroupAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        FoodGroupDbHelper foodGroupHelper = new FoodGroupDbHelper(this);


        ArrayList<String> groupNames = new ArrayList<String>();
        for(FoodGroup group : foodGroupHelper.findAll()) {
            groupNames.add(group.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.food_group_selection);
        spinner.setAdapter(adapter);

    }

    public void onSubmitNewFoodClick(View submitNewFood) {
        FoodGroupDbHelper groupDbHelper = new FoodGroupDbHelper(this);
        FoodDbHelper foodDbHelper = new FoodDbHelper(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            EditText foodName = findViewById(R.id.food_name_input);
            String name = foodName.getText().toString();
            EditText foodDate = findViewById(R.id.food_date_input);
            Date date = dateFormat.parse(foodDate.getText().toString());
            Spinner spinner = findViewById(R.id.food_group_selection);
            FoodGroup group = groupDbHelper.findByName(spinner.getSelectedItem().toString()).get(0);

            Food food = new Food(name, group.getId(), date);
            foodDbHelper.save(food);
        }
        catch (Exception e) {
            Log.d("date parsing", "onSubmitNewFoodClick: " + e.toString());
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
