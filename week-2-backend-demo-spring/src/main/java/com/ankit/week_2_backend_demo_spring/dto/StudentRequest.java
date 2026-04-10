package com.ankit.week_2_backend_demo_spring.dto;

import jakarta.validation.constraints.*;

public class StudentRequest {
    @NotNull(message = "Id cannnot be null")
    private Integer id;

    @NotNull(message = "Roll cannot be null")
    @Min(value = 1, message = "Roll must be >=1")
    private Integer roll;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Section cannot be empty")
    private String section;

    @NotBlank(message = "Branch cannot be empty")
    private String branch;

    // Default Constructor
    public StudentRequest() {

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
