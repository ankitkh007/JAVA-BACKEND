package com.ankit.week_5_relationships.dto;

public class CourseResponse {
    private Integer id;
    private String title;

    public CourseResponse() {

    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
