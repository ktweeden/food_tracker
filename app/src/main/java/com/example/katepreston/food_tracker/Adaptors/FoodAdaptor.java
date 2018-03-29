package com.example.katepreston.food_tracker.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;

/**
 * Created by katepreston on 24/03/2018.
 */

public class FoodAdaptor extends ArrayAdapter<Food>{
    View.OnClickListener listener;

    public FoodAdaptor(Context context, ArrayList<Food> foodList, View.OnClickListener listener) {
        super(context, 0, foodList);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View foodListView, ViewGroup parent) {
        FoodGroupDbHelper foodGroupDbHelper = new FoodGroupDbHelper(this.getContext());
        Food currentFood = getItem(position);
        if(foodListView == null) {
            foodListView = LayoutInflater.from(getContext()).inflate(R.layout.meal_food, parent, false);
        }

        TextView name = foodListView.findViewById(R.id.meal_food_name);
        name.setText(currentFood.getName());

        TextView group = foodListView.findViewById(R.id.meal_food_foodgroup);
        group.setText(foodGroupDbHelper.findByid(currentFood.getFoodGroup()).get(0).getName());

        ImageButton button = foodListView.findViewById(R.id.delete_meal_food);
        button.setTag(currentFood);
        button.setOnClickListener(listener);


        foodListView.setTag(currentFood);

        return foodListView;
    }
}
