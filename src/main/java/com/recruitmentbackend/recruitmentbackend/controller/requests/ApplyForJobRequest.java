package com.recruitmentbackend.recruitmentbackend.controller.requests;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 13:05
 * Project: Recruitment-Backend
 * Copyright: MIT
 */

@Value
@Getter
public class ApplyForJobRequest {
    UUID candidateId;
    UUID jobOfferId;
}
