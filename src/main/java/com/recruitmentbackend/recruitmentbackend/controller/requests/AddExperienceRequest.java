package com.recruitmentbackend.recruitmentbackend.controller.requests;

import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Value
@Getter
public class AddExperienceRequest {
    UUID userId;
    String title;
    LocalDate startDate;
    LocalDate endDate;
    String description;
}
