package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-21
 * Time: 10:53
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Getter
@Value
public class ChangListForCandidateRequest {
    UUID candidateId;
    UUID oldRecruitmentId;
    UUID newRecruitmentId;
}
