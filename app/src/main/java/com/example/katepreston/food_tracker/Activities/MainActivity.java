package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.katepreston.food_tracker.Adaptors.FoodAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoodDbHelper foodDbHelper = new FoodDbHelper(this);
        FoodAdaptor foodAdaptor = new FoodAdaptor(this, foodDbHelper.findAll());
        ListView listview = findViewById(R.id.food_list_view);
        listview.setAdapter(foodAdaptor);
    }

    public void onAddNewFoodClick(View listFoods) {
        Intent intent = new Intent(this, AddFoodActivity.class);

        startActivity(intent);
    }

    public void onSingleFoodItemClick(View foodItem) {
        Food selectedFood = (Food) foodItem.getTag();
        Intent intent = new Intent(this, SingleFoodActivity.class);
        intent.putExtra("food",selectedFood);
        startActivity(intent);
    }
}
