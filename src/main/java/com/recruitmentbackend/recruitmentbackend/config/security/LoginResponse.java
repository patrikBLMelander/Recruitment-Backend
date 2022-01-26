package com.recruitmentbackend.recruitmentbackend.config.security;

import lombok.Setter;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.List;

@Value
@Setter
public class LoginResponse {
    String username;
    HttpStatus httpStatus;
    String jwtToken;
}
