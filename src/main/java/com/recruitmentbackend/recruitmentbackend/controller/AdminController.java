package com.recruitmentbackend.recruitmentbackend.controller;

import com.recruitmentbackend.recruitmentbackend.controller.requests.CreateNewAdminRequest;
import com.recruitmentbackend.recruitmentbackend.controller.requests.CreateNewJobOfferRequest;
import com.recruitmentbackend.recruitmentbackend.controller.requests.RegisterCandidateRequest;
import com.recruitmentbackend.recruitmentbackend.services.CandidateService;
import com.recruitmentbackend.recruitmentbackend.services.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.recruitmentbackend.recruitmentbackend.controller.AppConstants.API_MAPPING.*;
/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:27
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RestController
@RequestMapping(BASE_API + ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final CandidateService candidateService;
    private final JobOfferService jobOfferService;


    @PostMapping(CREATE)
    public ResponseEntity<String> createAdmin(@RequestBody CreateNewAdminRequest createNewAdminRequest) {
        var result = candidateService.createAdmin(createNewAdminRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //ToDo: Skapa JobOffer, inparam Body av joboffer
    @PostMapping(JOB_OFFER+CREATE)
    public ResponseEntity<String> createJobOffer(@RequestBody CreateNewJobOfferRequest createNewJobOfferRequest) {
        var result = jobOfferService.createJobOffer(createNewJobOfferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    //ToDo: Hämta recruitment, inparam jobOffer_id

    //ToDo: Uppdatera RecruitmentListors ordning, inparam Body av JobOffer, (uppdatera JobOffer i frontend och skicka in den färdiga listan)

    //ToDo: Uppdatera RecruitmentListors kandidater, inparam Body av JobOffer, (uppdatera JobOffer i frontend och skicka in de färdiga listorna där kandidaterna fått ny plats)

    //ToDo: SÄtt Rate på Candidate, inparam JobOffer_id, Candidate_id, Rate

    //ToDo: Ändra visningsnamn på Candidates, inparam candidate_id, showedNickname



}
