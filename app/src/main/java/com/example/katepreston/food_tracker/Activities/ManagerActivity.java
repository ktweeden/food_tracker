package com.example.katepreston.food_tracker.Activities;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.FoodGroupDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Database.Helpers.SeedDbHelper;
import com.example.katepreston.food_tracker.Models.Food;
import com.example.katepreston.food_tracker.Models.FoodGroup;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.Date;

public class ManagerActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        SeedDbHelper.seed(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        Fragment mainActivity = new MainActivity();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, mainActivity).commit();

    }

    @Override
    public void onResume() {
        super.onResume();

        drawerLayout = findViewById(R.id.drawer_layout);
        context = this;


        NavigationView navigationView = findViewById(R.id.hamburger_nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();

                        Fragment targetFragment;

                        if (menuItem.getItemId() == R.id.nav_add_meal) {
                            targetFragment = new AddMealActivity();
                        }
                        else {
                            targetFragment = new MainActivity();
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, targetFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, new AddMealActivity());
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onEditListMealClick(View view) {
        Meal selectedMeal = (Meal) view.getTag();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("meal", selectedMeal);
        SingleMealActivity singleMealActivity = new SingleMealActivity();
        singleMealActivity.setArguments(args);
        transaction.replace(R.id.content_frame, singleMealActivity);

        transaction.addToBackStack(null);
        transaction.commit();
    }

        public void onSubmitNewMealClick(View submitNewMeal) {
        MealDbHelper mealDbHelper = new MealDbHelper(this);

        EditText mealName = findViewById(R.id.meal_name_input);
        String name = mealName.getText().toString();

        Spinner spinner = findViewById(R.id.rating_spinner);
        Rating rating = Rating.valueOf(spinner.getSelectedItem().toString());

        EditText mealDate = findViewById(R.id.meal_date_input);
        Date date = Utils.stringToDate(mealDate.getText().toString());

        Meal meal = new Meal(date, name, rating);

        mealDbHelper.save(meal);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("meal", meal);
        SingleMealActivity singleMealActivity = new SingleMealActivity();
        singleMealActivity.setArguments(args);
        transaction.replace(R.id.content_frame, singleMealActivity);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onAddFoodToMealClick(View addFoodButton) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View addFood = layoutInflater.inflate(R.layout.activity_add_food, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(addFood);
        final FoodGroupDbHelper foodGroupHelper = new FoodGroupDbHelper(this);
        final FoodDbHelper foodDbHelper = new FoodDbHelper(this);


        final Meal meal = (Meal) addFoodButton.getTag();

        alertDialogBuilder.setTitle("Add food to " + meal.getName());

        ArrayList<String> groupNames = new ArrayList<>();
        for(FoodGroup group : foodGroupHelper.findAll()) {
            groupNames.add(group.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groupNames);
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

                alert.dismiss();
            }
        });

    }


    public void onDeleteFoodFromMealClick(View deleteFoodButton) {
        Food food = (Food) deleteFoodButton.getTag();
        MealDbHelper mealDbHelper = new MealDbHelper(this);
        FoodDbHelper foodDbHelper = new FoodDbHelper(this);
        foodDbHelper.delete(food);
        Meal meal = mealDbHelper.findById(food.getId()).get(0);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("meal", meal);
        SingleMealActivity singleMealActivity = new SingleMealActivity();
        singleMealActivity.setArguments(args);
        transaction.replace(R.id.content_frame, singleMealActivity);

        transaction.addToBackStack(null);
        transaction.commit();
    }

}
