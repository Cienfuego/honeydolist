package com.example.backend.dto;

// src/main/java/com/yourcompany/yourapp/dto/LoginResponse.java

public class LoginResponse {
    private Long id;
    private String username;
    private String message;

    public LoginResponse() {}

    public LoginResponse(Long id, String username, String message) {
        this.id = id;
        this.username = username;
        this.message = message;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

