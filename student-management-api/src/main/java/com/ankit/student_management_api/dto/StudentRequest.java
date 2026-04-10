package com.ankit.student_management_api.dto;

import jakarta.validation.constraints.*;

public class StudentRequest {
    @NotNull(message = "Roll cannot be null!")
    @Min(value = 1, message = "Roll should be >=1")
    private Integer roll;

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Branch cannot be empty!")
    private String branch;

    @NotBlank(message = "Section cannot be empty!")
    private String section;

    // Default Contructor
    public StudentRequest() {

    }

    // Getters & Setters
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
