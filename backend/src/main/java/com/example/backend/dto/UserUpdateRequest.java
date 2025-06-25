package com.example.backend.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String username;
    public String getUsername() {
        return username;
    }

    private String password;
    public String getPassword() {
        return password;
    }



}
