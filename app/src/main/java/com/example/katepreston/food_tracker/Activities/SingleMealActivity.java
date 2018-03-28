package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Adaptors.FoodAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

public class SingleMealActivity extends Fragment {

    View view;
    Meal selectedMeal;
    FoodDbHelper foodDbHelper;
    FoodAdaptor foodAdaptor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        Meal selectedMeal = (Meal) bundle.getSerializable("meal");

        view = inflater.inflate(R.layout.activity_single_meal, container, false);
        foodDbHelper = new FoodDbHelper(view.getContext());
        foodAdaptor = new FoodAdaptor(view.getContext(), foodDbHelper.findByMealid(selectedMeal.getId()));

        getActivity().setTitle(selectedMeal.getName() + ": " + Utils.dateToString(selectedMeal.getDate()));

        TextView date = view.findViewById(R.id.single_meal_date);
        date.setText(Utils.dateToString(selectedMeal.getDate()));


        TextView ragColour = view.findViewById(R.id.single_meal_rag_rating_colour);
        if (selectedMeal.getRating() == Rating.RED) {
            ragColour.setBackgroundColor(view.getResources().getColor(R.color.ragRed));
        }
        else if (selectedMeal.getRating() == Rating.AMBER) {
            ragColour.setBackgroundColor(view.getResources().getColor(R.color.ragAmber));
        }
        else if (selectedMeal.getRating() == Rating.AMBER) {
            ragColour.setBackgroundColor(view.getResources().getColor(R.color.ragGreen));
        }


        Button addFoodButton = view.findViewById(R.id.add_food_to_meal);
        addFoodButton.setTag(selectedMeal);

        ListView listview = view.findViewById(R.id.foods_in_meal_list);
        listview.setAdapter(foodAdaptor);

        Button editButton = view.findViewById(R.id.edit_meal_button);
        editButton.setTag(selectedMeal);

        Button deleteButton = view.findViewById(R.id.delete_meal_button);
        deleteButton.setTag(selectedMeal);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        ListView listview = view.findViewById(R.id.foods_in_meal_list);
        listview.setAdapter(foodAdaptor);
    }

}
