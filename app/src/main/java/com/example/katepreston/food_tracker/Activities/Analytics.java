package com.example.katepreston.food_tracker.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.katepreston.food_tracker.Database.Helpers.MealDbHelper;
import com.example.katepreston.food_tracker.Models.Rating;
import com.example.katepreston.food_tracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Analytics extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_analytics, container, false);
        getActivity().setTitle("Analytics");
        populateProgressBars();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateProgressBars();
    }

    private void populateProgressBars() {

        MealDbHelper mealDbHelper = new MealDbHelper(getContext());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date weekPreviously = calendar.getTime();

        Calendar monthcalendar = Calendar.getInstance();
        monthcalendar.add(Calendar.MONTH, -1);
        Date monthPreviously = calendar.getTime();


        int weekGreenNumber = mealDbHelper.findAllSinceDateWithRating(weekPreviously, Rating.GREEN).size();
        int weekAmberNumber = mealDbHelper.findAllSinceDateWithRating(weekPreviously, Rating.AMBER).size();
        int weekRedNumber = mealDbHelper.findAllSinceDateWithRating(weekPreviously, Rating.RED).size();

        int weekTotal = weekAmberNumber + weekGreenNumber + weekRedNumber;

        int monthGreenNumber = mealDbHelper.findAllSinceDateWithRating(monthPreviously, Rating.GREEN).size();
        int monthAmberNumber = mealDbHelper.findAllSinceDateWithRating(monthPreviously, Rating.AMBER).size();
        int monthRedNumber = mealDbHelper.findAllSinceDateWithRating(monthPreviously, Rating.RED).size();

        int monthTotal = monthAmberNumber + monthGreenNumber + monthRedNumber;

        ProgressBar wkGreenProgress = view.findViewById(R.id.wk_green);
        wkGreenProgress.setMax(weekTotal);
        wkGreenProgress.setProgress(weekGreenNumber);
        ProgressBar wkAmberProgress = view.findViewById(R.id.wk_amber);
        wkAmberProgress.setMax(weekTotal);
        wkAmberProgress.setProgress(weekAmberNumber);
        ProgressBar wkRedProgress = view.findViewById(R.id.wk_red);
        wkRedProgress.setMax(weekTotal);
        wkRedProgress.setProgress(weekRedNumber);

        ProgressBar mnthGreenProgress = view.findViewById(R.id.mnth_green);
        mnthGreenProgress.setMax(monthTotal);
        mnthGreenProgress.setProgress(monthGreenNumber);
        ProgressBar mnthAmberProgress = view.findViewById(R.id.mnth_amber);
        mnthAmberProgress.setMax(monthTotal);
        mnthAmberProgress.setProgress(monthAmberNumber);
        ProgressBar mnthRedProgress = view.findViewById(R.id.mnth_red);
        mnthRedProgress.setMax(monthTotal);
        mnthRedProgress.setProgress(monthRedNumber);

    }

}
