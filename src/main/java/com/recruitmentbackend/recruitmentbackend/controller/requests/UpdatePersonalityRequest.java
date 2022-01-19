package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 15:37
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class UpdatePersonalityRequest {
    UUID userId;
    Integer openness;
    Integer conscientiousness;
    Integer extroversion;
    Integer agreeableness;
    Integer neuroticism;
}
