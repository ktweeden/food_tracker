package com.example.katepreston.food_tracker.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by katepreston on 26/03/2018.
 */

public class Meal implements Serializable {
    private Long id;
    private Date date;
    private String name;
    private Rating rating;

    public Meal(Date date, String name, Rating rating) {
        this.date = date;
        this.name = name;
        this.rating = rating;
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }
}
