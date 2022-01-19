package com.recruitmentbackend.recruitmentbackend.controller.requests;

import com.recruitmentbackend.recruitmentbackend.models.Competence;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Patrik Melander
 * Date: 2022-01-19
 * Time: 09:24
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@AllArgsConstructor
public class CreateNewJobOfferRequest {
    String title;
    LocalDate applyDate;
    String preview;
    String companyDescription;
    String aboutRole;
    List<Competence> competenceList;
    String location;
}
