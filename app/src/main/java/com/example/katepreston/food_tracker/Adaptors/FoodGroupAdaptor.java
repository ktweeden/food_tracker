package com.example.katepreston.food_tracker.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;

/**
 * Created by katepreston on 24/03/2018.
 */

public class FoodGroupAdaptor extends ArrayAdapter<FoodGroup>{
    public FoodGroupAdaptor(Context context, ArrayList<FoodGroup> foodList) {
        super(context, 0, foodList);
    }

    @Override
    public View getView(int position, View foodListView, ViewGroup parent) {
        FoodGroup currentFoodGroup = getItem(position);
        if(foodListView == null) {
            foodListView = LayoutInflater.from(getContext()).inflate(R.layout.single_food_group, parent, false);
        }

        TextView name = foodListView.findViewById(R.id.food_group_name);
        name.setText(currentFoodGroup.getName());

        foodListView.setTag(currentFoodGroup);

        return foodListView;
    }
}
