package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-20
 * Time: 13:12
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
@AllArgsConstructor
public class GetFullDetailJobOfferRequest {
    UUID jobOfferId;
    String test;
}
