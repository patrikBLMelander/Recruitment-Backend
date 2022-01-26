package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 14:38
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class UpdatePresentationRequest {
    String userId;
    String presentation;

}
