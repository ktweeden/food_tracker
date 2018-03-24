package com.example.katepreston.food_tracker.Models;

/**
 * Created by katepreston on 23/03/2018.
 */

public class FoodGroup {
    private String name;
    private Long id;

    public FoodGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
