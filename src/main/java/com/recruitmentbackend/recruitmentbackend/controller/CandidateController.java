package com.recruitmentbackend.recruitmentbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.recruitmentbackend.recruitmentbackend.controller.AppConstants.API_MAPPING.*;
/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:26
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RestController
@RequestMapping(BASE_API + CANDIDATES)
@RequiredArgsConstructor
public class CandidateController {

    //ToDo: Add/Update Description

    //ToDo: Lägg till Experience
    //ToDo: Ta bort Experience

    //ToDo: Lägg till Education
    //ToDo: Ta bort Education

    //ToDo: Lägg till Competence
    //ToDo: Ta bort Competence

    //ToDo: Uppdatera Personality

    //ToDo: Apply for job

    //ToDo: See all job Applied for

    //ToDo: Spara färgSchema, inparam candidate_id, ColorChoice

    //ToDo: Change Password, inparam Body av Candidate
}
