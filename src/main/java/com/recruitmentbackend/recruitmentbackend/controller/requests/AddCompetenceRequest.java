package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 14:22
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class AddCompetenceRequest {
    UUID userId;
    String name;
    int value;
}
