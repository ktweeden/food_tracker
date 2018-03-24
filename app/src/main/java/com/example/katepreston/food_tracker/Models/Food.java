package com.example.katepreston.food_tracker.Models;

import java.util.Date;

/**
 * Created by katepreston on 23/03/2018.
 */

public class Food {
    private Long id;
    private String name;
    private FoodGroup foodGroup;
    private Date date;

    public Food(String name, FoodGroup foodGroup, Date date) {
        this.name = name;
        this.foodGroup = foodGroup;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FoodGroup getFoodGroup() {
        return foodGroup;
    }

    public Date getDate() {
        return date;
    }
}
