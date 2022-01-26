package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;


/**
 * Created by Patrik Melander
 * Date: 2022-01-20
 * Time: 08:30
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class UpdatePasswordRequest {
    UUID userId;
    String newPassword;
    String oldPassword;
}
