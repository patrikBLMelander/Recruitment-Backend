package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 15:15
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@AllArgsConstructor
public class RegisterCandidateRequest {
    int nickName;
    String firstName;
    String lastName;
    String phone;
    String email;
    String password;
}
