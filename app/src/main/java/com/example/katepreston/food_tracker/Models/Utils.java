package com.example.katepreston.food_tracker.Models;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by katepreston on 25/03/2018.
 */

public class Utils {

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static Date stringToDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            Log.d("stringToDate", "stringToDate: " + e.toString());
        }
        return newDate;
    }
}
