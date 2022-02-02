package com.recruitmentbackend.recruitmentbackend.models.DTO;

import com.recruitmentbackend.recruitmentbackend.models.Competence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-20
 * Time: 12:54
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
@AllArgsConstructor
public class CandidateJobOfferDTO {
    UUID id;
    String title;
    LocalDate publishDate;
    LocalDate applyDate;
    String preview;
    String companyDescription;
    String aboutRole;
    String location;
    String imageUrl;
    int totalCandidates;
    int newCandidates;
    List<Competence> competenceList;
}
