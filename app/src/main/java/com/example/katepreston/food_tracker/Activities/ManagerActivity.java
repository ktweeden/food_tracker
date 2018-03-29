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
                        else if (menuItem.getItemId() == R.id.nav_analytics) {
                            targetFragment = new Analytics();
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

}
