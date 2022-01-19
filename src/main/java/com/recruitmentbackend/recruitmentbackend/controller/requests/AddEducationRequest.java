package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 14:08
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class AddEducationRequest {
    UUID userId;
    String title;
    LocalDate startDate;
    LocalDate endDate;
    String description;
}
