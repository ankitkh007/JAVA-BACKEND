package com.ankit.week_5_relationships.dto;

public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest() {

    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}