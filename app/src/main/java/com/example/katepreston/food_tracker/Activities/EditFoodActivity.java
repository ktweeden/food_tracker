package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.Date;

public class EditFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        FoodGroupDbHelper foodGroupHelper = new FoodGroupDbHelper(this);

        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("food");

        EditText foodName = findViewById(R.id.edit_food_name_input);
        foodName.setText(food.getName());

        ArrayList<String> groupNames = new ArrayList<>();
        for(FoodGroup group : foodGroupHelper.findAll()) {
            groupNames.add(group.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.edit_food_group_input);
        spinner.setAdapter(adapter);

        int spinnerPosition = groupNames.indexOf(foodGroupHelper.findByid(food.getFoodGroup()).get(0).getName());
        spinner.setSelection(spinnerPosition);

        Button submitButton = findViewById(R.id.submit_edit_button);
        submitButton.setTag(food);
    }

    public void onSubmitEditsClick(View submitButton) {
        FoodGroupDbHelper groupDbHelper = new FoodGroupDbHelper(this);
        FoodDbHelper foodDbHelper = new FoodDbHelper(this);

        EditText foodName = findViewById(R.id.edit_food_name_input);
        String name = foodName.getText().toString();

        Spinner spinner = findViewById(R.id.edit_food_group_input);
        FoodGroup group = groupDbHelper.findByName(spinner.getSelectedItem().toString()).get(0);

        Food food = (Food) submitButton.getTag();
        Food updatedFood = new Food(name, group.getId(), food.getMeal());
        updatedFood.setId(food.getId());
        foodDbHelper.update(updatedFood);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
