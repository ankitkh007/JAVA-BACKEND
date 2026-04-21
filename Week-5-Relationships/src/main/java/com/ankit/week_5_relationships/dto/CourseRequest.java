package com.ankit.week_5_relationships.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseRequest {
    @NotBlank(message = "title cannot be blank!")
    private String title;

    public CourseRequest() {

    }

    // Getters & Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
