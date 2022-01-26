package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-20
 * Time: 09:33
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class SetRateOnCandidateRequest {
    String candidateId;
    String jobOfferId;
    double rate;
}
