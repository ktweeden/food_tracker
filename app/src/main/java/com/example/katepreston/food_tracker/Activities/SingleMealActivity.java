package com.example.katepreston.food_tracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katepreston.food_tracker.Adaptors.FoodAdaptor;
import com.example.katepreston.food_tracker.Database.Helpers.FoodDbHelper;
import com.example.katepreston.food_tracker.Models.Meal;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.Models.Utils;
import com.example.katepreston.food_tracker.R;

public class SingleMealActivity extends AppCompatActivity {


    private FoodAdaptor foodAdaptor;
    private FoodDbHelper foodDbHelper;
    private Meal selectedMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_meal);

        foodDbHelper = new FoodDbHelper(this);
        Intent intent = getIntent();
        selectedMeal = (Meal) intent.getSerializableExtra("meal");
        foodAdaptor = new FoodAdaptor(this, foodDbHelper.findByMealid(selectedMeal.getId()));

        TextView name = findViewById(R.id.single_meal_name);
        name.setText(selectedMeal.getName());

        TextView date = findViewById(R.id.single_meal_date);
        date.setText(Utils.dateToString(selectedMeal.getDate()));


        TextView ragColour = findViewById(R.id.single_meal_rag_rating_colour);
        if (selectedMeal.getRating() == Rating.RED) {
            ragColour.setBackgroundColor(getResources().getColor(R.color.ragRed));
        }
        else if (selectedMeal.getRating() == Rating.AMBER) {
            ragColour.setBackgroundColor(getResources().getColor(R.color.ragAmber));
        }
        else if (selectedMeal.getRating() == Rating.AMBER) {
            ragColour.setBackgroundColor(getResources().getColor(R.color.ragGreen));
        }


        Button addFoodButton = findViewById(R.id.add_food_to_meal);
        addFoodButton.setTag(selectedMeal);

        ListView listview = findViewById(R.id.foods_in_meal_list);
        listview.setAdapter(foodAdaptor);

        Button editButton = findViewById(R.id.edit_meal_button);
        editButton.setTag(selectedMeal);

        Button deleteButton = findViewById(R.id.delete_meal_button);
        deleteButton.setTag(selectedMeal);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        foodAdaptor = new FoodAdaptor(this, foodDbHelper.findByMealid(selectedMeal.getId()));
        ListView listview = findViewById(R.id.foods_in_meal_list);
        listview.setAdapter(foodAdaptor);
    }


    public void onAddFoodToMealClick(View addFoodButton) {

        Meal meal = (Meal) addFoodButton.getTag();

        Intent intent = new Intent(this, AddFoodActivity.class);
        intent.putExtra("meal", meal);

        startActivity(intent);
    }
}
