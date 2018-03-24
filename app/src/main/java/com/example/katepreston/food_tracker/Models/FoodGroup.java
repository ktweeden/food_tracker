package com.example.katepreston.food_tracker.Models;

import java.io.Serializable;

/**
 * Created by katepreston on 23/03/2018.
 */

public class FoodGroup implements Serializable {
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
