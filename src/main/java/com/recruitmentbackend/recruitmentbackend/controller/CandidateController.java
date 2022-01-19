package com.recruitmentbackend.recruitmentbackend.controller;

import com.recruitmentbackend.recruitmentbackend.controller.requests.*;
import com.recruitmentbackend.recruitmentbackend.services.CandidateService;
import com.recruitmentbackend.recruitmentbackend.services.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final JobOfferService jobOfferService;
    private final CandidateService candidateService;


    @PostMapping(JOB_OFFER)
    public ResponseEntity<String> applyForJob(@RequestBody ApplyForJobRequest request) {
        var result = jobOfferService.applyForJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping(EXPERIENCE)
    public ResponseEntity<?> addExperience(@RequestBody AddExperienceRequest experienceRequest) {
        var result = candidateService.addExperience(experienceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PostMapping(EDUCATION)
    public ResponseEntity<?> addEducation(@RequestBody AddEducationRequest educationRequest) {
        var result = candidateService.addEducation(educationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PostMapping(COMPETENCE)
    public ResponseEntity<?> addCompetence(@RequestBody AddCompetenceRequest competenceRequest) {
        var result = candidateService.addCompetence(competenceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping(UPDATE+PRESENTATION)
    public ResponseEntity<?> updatePresentation(@RequestBody UpdatePresentationRequest updatePresentationRequest) {
        var result = candidateService.updatePresentation(updatePresentationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping(UPDATE+PERSONALITY)
    public ResponseEntity<?> updatePersonality(@RequestBody UpdatePersonalityRequest updatePersonalityRequest) {
        var result = candidateService.updatePersonality(updatePersonalityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    //ToDo: Add/Update Description

    //ToDo: Lägg till Experience
    //ToDo: Ta bort Experience

    //ToDo: Lägg till Education
    //ToDo: Ta bort Education

    //ToDo: Lägg till Competence
    //ToDo: Ta bort Competence

    //ToDo: Uppdatera Personality



    //ToDo: See all job Applied for

    //ToDo: Spara färgSchema, inparam candidate_id, ColorChoice

    //ToDo: Change Password, inparam Body av Candidate
}
