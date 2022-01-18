package com.recruitmentbackend.recruitmentbackend.config.security;

import lombok.Setter;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@Setter
public class LoginResponse {
    String username;
    HttpStatus httpStatus;
    String jwtToken;
}
