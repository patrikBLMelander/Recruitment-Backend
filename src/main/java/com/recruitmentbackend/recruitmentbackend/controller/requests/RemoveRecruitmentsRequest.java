package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-21
 * Time: 09:00
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Getter
@Value
public class RemoveRecruitmentsRequest {
    UUID recruitmentId;
    UUID jobOfferId;
}
