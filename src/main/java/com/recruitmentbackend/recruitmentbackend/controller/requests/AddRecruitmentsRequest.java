package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-21
 * Time: 08:37
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Getter
@Value
public class AddRecruitmentsRequest {
    UUID jobOfferId;
    String title;
}
