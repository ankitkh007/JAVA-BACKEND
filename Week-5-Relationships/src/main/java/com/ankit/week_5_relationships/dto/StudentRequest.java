package com.ankit.week_5_relationships.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class StudentRequest {
    @NotBlank(message = "Name cannot be empty!")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
    private String name;

    @NotEmpty(message = "Courses cannot be empty!")
    @Valid // ---> Nested Validation
    private List<CourseRequest> courses;

    // Default Constructor
    public StudentRequest() {

    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseRequest> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseRequest> courses) {
        this.courses = courses;
    }
}
