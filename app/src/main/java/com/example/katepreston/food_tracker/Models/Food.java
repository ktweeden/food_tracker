package com.example.katepreston.food_tracker.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by katepreston on 23/03/2018.
 */

public class Food implements Serializable {
    private Long id;
    private String name;
    private Long foodGroup;
    private Long meal;

    public Food(String name, Long foodGroup, Long meal) {
        this.name = name;
        this.foodGroup = foodGroup;
        this.meal = meal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMeal(Long mealId) {
        this.meal = mealId;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getFoodGroup() {
        return this.foodGroup;
    }

    public Long getMeal() {
        return this.meal;
    }
}
