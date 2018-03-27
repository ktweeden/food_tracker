package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.katepreston.food_tracker.Adaptors.FoodAdaptor;
import com.example.katepreston.food_tracker.Adaptors.MealAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.SeedDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Meal> meals;
    private HashMap<Meal, ArrayList<Food>> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeedDbHelper.seed(this);
        prepareListData();

        ExpandableListView listView = findViewById(R.id.meal_list);
        MealAdaptor mealAdaptor = new MealAdaptor(this, this.meals, this.foods);
        listView.setAdapter(mealAdaptor);

    }

    public void onAddNewMealClick(View listFoods) {
        Intent intent = new Intent(this, AddMealActivity.class);

        startActivity(intent);
    }

    public void prepareListData() {
        MealDbHelper mealDbHelper = new MealDbHelper(this);
        FoodDbHelper foodDbHelper = new FoodDbHelper(this);
        this.meals = mealDbHelper.findAll();
        this.foods = new HashMap<>();

        for (Meal meal : this.meals) {
            ArrayList<Food>foodList = foodDbHelper.findByMealid(meal.getId());
            this.foods.put(meal, foodList);
        }
    }

//    public void onSingleFoodItemClick(View foodItem) {
//        Food selectedFood = (Food) foodItem.getTag();
//        Intent intent = new Intent(this, SingleFoodActivity.class);
//        intent.putExtra("food",selectedFood);
//        startActivity(intent);
//    }
}
