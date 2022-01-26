package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 09:04
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class CreateNewAdminRequest {
    String firstName;
    String lastName;
    String email;
    String password;
}
