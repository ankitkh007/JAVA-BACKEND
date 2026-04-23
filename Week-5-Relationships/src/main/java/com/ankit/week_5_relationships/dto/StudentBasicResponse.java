package com.ankit.week_5_relationships.dto;

public class StudentBasicResponse {
    private Integer id;
    private String name;

    // Default Constructor
    public StudentBasicResponse() {

    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
