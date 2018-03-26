package com.example.katepreston.food_tracker.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public FoodAdaptor(Context context, ArrayList<Food> foodList) {
        super(context, 0, foodList);
    }

    @Override
    public View getView(int position, View foodListView, ViewGroup parent) {
        FoodGroupDbHelper foodGroupDbHelper = new FoodGroupDbHelper(this.getContext());
        Food currentFood = getItem(position);
        if(foodListView == null) {
            foodListView = LayoutInflater.from(getContext()).inflate(R.layout.single_food, parent, false);
        }

        TextView name = foodListView.findViewById(R.id.food_name);
        name.setText(currentFood.getName());

        TextView date = foodListView.findViewById(R.id.single_food_date);
        date.setText(Utils.dateToString(currentFood.getDate()));

        TextView group = foodListView.findViewById(R.id.single_food_foodgroup);
        group.setText(foodGroupDbHelper.findByid(currentFood.getFoodGroup()).get(0).getName());

        foodListView.setTag(currentFood);

        return foodListView;
    }
}
