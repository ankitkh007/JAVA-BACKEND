package com.ankit.week_5_relationships.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseRequest {
    @NotBlank(message = "title cannot be blank!")
    @Size(min = 2, max = 100, message = "Course title must be valid")
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
