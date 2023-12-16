package com.example.rip.models.dtos.response;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
