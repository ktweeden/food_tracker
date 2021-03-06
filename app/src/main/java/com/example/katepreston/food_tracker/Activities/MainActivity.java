package com.example.katepreston.food_tracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.katepreston.food_tracker.Adaptors.FoodAdaptor;
import com.example.katepreston.food_tracker.Adaptors.MealAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.SeedDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Fragment implements View.OnClickListener{

    private ArrayList<Meal> meals;
    private HashMap<Meal, ArrayList<Food>> foods;
    private Context context;
    private MealAdaptor mealAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        getActivity().setTitle(R.string.app_name);
        prepareListData();

        View view = inflater.inflate(R.layout.activity_main, container, false);

        ExpandableListView listView = view.findViewById(R.id.meal_list);
        mealAdaptor = new MealAdaptor(getActivity(), this.meals, this.foods, this);
        listView.setAdapter(mealAdaptor);

        return view;
    }

    public void prepareListData() {
        MealDbHelper mealDbHelper = new MealDbHelper(context);
        FoodDbHelper foodDbHelper = new FoodDbHelper(context);
        this.meals = mealDbHelper.findAll();
        this.foods = new HashMap<>();

        for (Meal meal : this.meals) {
            ArrayList<Food>foodList = foodDbHelper.findByMealid(meal.getId());
            this.foods.put(meal, foodList);
        }
    }

    @Override
    public void onClick(View view) {
        Meal selectedMeal = (Meal) view.getTag();


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("meal", selectedMeal);
        SingleMealActivity singleMealActivity = new SingleMealActivity();
        singleMealActivity.setArguments(args);
        transaction.replace(R.id.content_frame, singleMealActivity);

        transaction.addToBackStack(null);
        transaction.commit();
    }
}
