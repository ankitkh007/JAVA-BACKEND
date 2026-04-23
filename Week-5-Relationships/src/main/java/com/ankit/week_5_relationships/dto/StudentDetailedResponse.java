package com.ankit.week_5_relationships.dto;

import java.util.*;

public class StudentDetailedResponse {
    private Integer id;
    private String name;
    private List<CourseResponse> courses;

    // Default Constructor
    public StudentDetailedResponse() {

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

    public List<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses;
    }
}
