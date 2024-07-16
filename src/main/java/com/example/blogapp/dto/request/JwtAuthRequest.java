package com.example.blogapp.dto.request;


import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}
