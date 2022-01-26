package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

/**
 * Created by Patrik Melander
 * Date: 2022-01-25
 * Time: 15:19
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Getter
@Value
public class CandidateDetails {
    String email;
    String test;
}
