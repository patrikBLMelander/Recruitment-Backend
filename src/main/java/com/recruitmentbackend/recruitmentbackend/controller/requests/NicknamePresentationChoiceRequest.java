package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-20
 * Time: 11:21
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class NicknamePresentationChoiceRequest {
    UUID candidateId;
    String nicknameChoice;
}
