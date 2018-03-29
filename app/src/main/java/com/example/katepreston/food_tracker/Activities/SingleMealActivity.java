package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Adaptors.FoodAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.Date;

public class SingleMealActivity extends Fragment implements View.OnClickListener {

    View view;
    FoodDbHelper foodDbHelper;
    FoodAdaptor foodAdaptor;
    ArrayList<Food> list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        final Meal selectedMeal = (Meal) bundle.getSerializable("meal");

        view = inflater.inflate(R.layout.activity_single_meal, container, false);
        foodDbHelper = new FoodDbHelper(view.getContext());

        list = foodDbHelper.findByMealid(selectedMeal.getId());

        foodAdaptor = new FoodAdaptor(view.getContext(), list , this);

        getActivity().setTitle(selectedMeal.getName() + ": " + Utils.dateToString(selectedMeal.getDate()));


        TextView ragColour = view.findViewById(R.id.single_meal_rag_rating_colour);
        if (selectedMeal.getRating() == Rating.RED) {
            ragColour.setBackgroundColor(view.getResources().getColor(R.color.ragRed));
        }
        else if (selectedMeal.getRating() == Rating.AMBER) {
            ragColour.setBackgroundColor(view.getResources().getColor(R.color.ragAmber));
        }
        else if (selectedMeal.getRating() == Rating.GREEN) {
            ragColour.setBackgroundColor(view.getResources().getColor(R.color.ragGreen));
        }


        Button addFoodButton = view.findViewById(R.id.add_food_to_meal);
        addFoodButton.setTag(selectedMeal);
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddFoodToMealClick(v);
            }
        });

        ListView listview = view.findViewById(R.id.foods_in_meal_list);
        listview.setAdapter(foodAdaptor);

        ImageButton editButton = view.findViewById(R.id.edit_meal_button);
        editButton.setTag(selectedMeal);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditMealClick(view);
            }
        });


        ImageButton deleteButton = view.findViewById(R.id.delete_meal_button);
        deleteButton.setTag(selectedMeal);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealDbHelper mealHelper = new MealDbHelper(getActivity());
                mealHelper.delete(selectedMeal);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                MainActivity mainActivity = new MainActivity();
                transaction.replace(R.id.content_frame, mainActivity);

                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

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

    public void onAddFoodToMealClick(View addFoodButton) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View addFood = layoutInflater.inflate(R.layout.activity_add_food, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(addFood);
        final FoodGroupDbHelper foodGroupHelper = new FoodGroupDbHelper(getActivity());
        final FoodDbHelper foodDbHelper = new FoodDbHelper(getActivity());


        final Meal meal = (Meal) addFoodButton.getTag();

        alertDialogBuilder.setTitle("Add food to " + meal.getName());

        ArrayList<String> groupNames = new ArrayList<>();
        for(FoodGroup group : foodGroupHelper.findAll()) {
            groupNames.add(group.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, groupNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = addFood.findViewById(R.id.food_group_selection);
        spinner.setAdapter(adapter);


        Button submit = addFood.findViewById(R.id.submit_new_food_button);
        submit.setTag(meal.getId());

        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        addFood.findViewById(R.id.submit_new_food_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText foodName = addFood.findViewById(R.id.food_name_input);
                String name = foodName.getText().toString();

                FoodGroup group = foodGroupHelper.findByName(spinner.getSelectedItem().toString()).get(0);
                Food food = new Food(name, group.getId(), meal.getId());
                foodDbHelper.save(food);

                list.add(food);

                alert.dismiss();
                foodAdaptor.notifyDataSetChanged();
            }
        });
    }

    public void onEditMealClick(View editbutton) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View editMeal = layoutInflater.inflate(R.layout.edit_single_meal, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(editMeal);

        final Meal selectedMeal = (Meal) editbutton.getTag();

        alertDialogBuilder.setTitle("Edit" + selectedMeal.getName());

        final MealDbHelper mealDbHelper = new MealDbHelper(getActivity());

        alertDialogBuilder.setTitle("Edit Meal");

        ArrayList<String> ratings = new ArrayList<>();
        for (Rating rating : Rating.values()) {
            ratings.add(rating.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = editMeal.findViewById(R.id.edit_meal_spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(ratings.indexOf(selectedMeal.getRating().name()));

        final EditText name = editMeal.findViewById(R.id.edit_meal_name);
        name.setText(selectedMeal.getName());
        final EditText date = editMeal.findViewById(R.id.edit_meal_date);
        date.setText(Utils.dateToString(selectedMeal.getDate()));

        Button button = editMeal.findViewById(R.id.submit_edit_meal);

        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = name.getText().toString();
                Date mealDate = Utils.stringToDate(date.getText().toString());
                Spinner mealRating = editMeal.findViewById(R.id.edit_meal_spinner);
                Rating rating = Rating.valueOf(mealRating.getSelectedItem().toString());

                Meal toUpdate = new Meal(mealDate, mealName, rating);
                toUpdate.setId(selectedMeal.getId());

                mealDbHelper.update(toUpdate);
                alert.dismiss();
            }
        });


    }


    @Override
    public void onClick(View view) {
        Food food = (Food) view.getTag();
        MealDbHelper mealDbHelper = new MealDbHelper(getActivity());
        FoodDbHelper foodDbHelper = new FoodDbHelper(getActivity());
        foodDbHelper.delete(food);
        Meal meal = mealDbHelper.findById(food.getMeal()).get(0);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("meal", meal);
        SingleMealActivity singleMealActivity = new SingleMealActivity();
        singleMealActivity.setArguments(args);
        transaction.replace(R.id.content_frame, singleMealActivity);

        transaction.addToBackStack(null);
        transaction.commit();
    }
}
