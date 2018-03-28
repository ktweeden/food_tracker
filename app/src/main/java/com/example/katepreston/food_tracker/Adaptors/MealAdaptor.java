package com.example.katepreston.food_tracker.Adaptors;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by katepreston on 27/03/2018.
 */

public class MealAdaptor extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Meal> meals;
    private HashMap<Meal, ArrayList<Food>> foods;

    public MealAdaptor(Context context, ArrayList<Meal> meals, HashMap<Meal, ArrayList<Food>> foods) {
        this.context = context;
        this.meals = meals;
        this.foods = foods;
    }

    @Override
    public int getGroupCount() {
        return meals.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return foods.get(meals.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.meals.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return foods.get(meals.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getGroupId(int position) {
        return position;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPostion, boolean isExpanded, View convertView, ViewGroup parent) {
        Meal currentMeal = (Meal) getGroup(groupPostion);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.single_meal, parent, false);
        }

        TextView name = convertView.findViewById(R.id.meal_list_name);
        name.setText(currentMeal.getName());
        TextView date = convertView.findViewById(R.id.meal_list_date);
        date.setText(Utils.dateToString(currentMeal.getDate()));

        ImageButton edit = convertView.findViewById(R.id.edit_list_meal);
        edit.setFocusable(false);
        edit.setTag(currentMeal);

        TextView ragColour = convertView.findViewById(R.id.meal_list_rag);
        if (currentMeal.getRating() == Rating.RED) {
            ragColour.setBackgroundResource(R.color.ragRed);
        }
        else if (currentMeal.getRating() == Rating.AMBER) {
            ragColour.setBackgroundResource(R.color.ragAmber);
        }
        else if (currentMeal.getRating() == Rating.GREEN) {
            ragColour.setBackgroundResource(R.color.ragGreen);
        }

        convertView.setTag(currentMeal);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Food currentFood = (Food) getChild(groupPosition, childPosition);
        FoodGroupDbHelper foodGroupDbHelper = new FoodGroupDbHelper(this.context);

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.single_food, parent, false);
        }

        if (currentFood != null) {
            TextView name = convertView.findViewById(R.id.food_name);
            name.setText(currentFood.getName());

            TextView foodGroup = convertView.findViewById(R.id.single_food_foodgroup);
            foodGroup.setText(foodGroupDbHelper.findByid(currentFood.getFoodGroup()).get(0).getName());

            convertView.setTag(currentFood);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
