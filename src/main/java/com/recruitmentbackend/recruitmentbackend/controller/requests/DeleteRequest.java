package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-20
 * Time: 14:11
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class DeleteRequest {
    UUID candidateId;
    UUID toRemove;
}
