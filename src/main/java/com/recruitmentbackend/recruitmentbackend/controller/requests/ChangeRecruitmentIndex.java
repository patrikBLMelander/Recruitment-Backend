package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-21
 * Time: 09:20
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
public class ChangeRecruitmentIndex {
    UUID recruitmentId;
    Integer newIndex;
    UUID jobOfferId;
}
