package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import org.w3c.dom.Text;

public class SingleFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        FoodGroupDbHelper groupHelper = new FoodGroupDbHelper(this);

        Intent intent = getIntent();
        Food selectedFood = (Food) intent.getSerializableExtra("food");
        TextView foodName = findViewById(R.id.single_food_name);
        foodName.setText(selectedFood.getName());

        TextView foodGroup = findViewById(R.id.single_food_group);
        FoodGroup group = groupHelper.findByid(selectedFood.getFoodGroup()).get(0);
        Log.d("food group", group.getName());
        foodGroup.setText(group.getName());

        Button editButton = findViewById(R.id.edit_food);
        editButton.setTag(selectedFood);

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setTag(selectedFood);

    }

    public void onEditButtonClick (View editButton) {
        Food food = (Food) editButton.getTag();

        Intent intent = new Intent(this, EditFoodActivity.class);
        intent.putExtra("food", food);

        startActivity(intent);
    }

    public void onDeleteButtonClick (View deleteButton) {
        FoodDbHelper foodHelper = new FoodDbHelper(this);
        Food food = (Food) deleteButton.getTag();
        foodHelper.delete(food);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
