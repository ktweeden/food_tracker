package com.example.katepreston.food_tracker.Activities;

import android.content.Context;
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
    ExpandableListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeedDbHelper.seed(this);
        prepareListData();
        context = this;

        listView = findViewById(R.id.meal_list);
        final MealAdaptor mealAdaptor = new MealAdaptor(this, this.meals, this.foods);
        listView.setAdapter(mealAdaptor);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Food selectedFood = (Food) mealAdaptor.getChild(groupPosition, childPosition);
                Intent intent = new Intent(context, SingleFoodActivity.class);
                intent.putExtra("food", selectedFood);
                startActivity(intent);

                return false;
            }
        });

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
    
}
