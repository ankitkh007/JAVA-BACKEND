package com.ankit.week_2_backend_demo_spring.model;

public class User {
    public int id;
    public String name;
    public String city;

    // Default Constructor
    public User() {
    };

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
