package com.recruitmentbackend.recruitmentbackend.controller;

import com.recruitmentbackend.recruitmentbackend.controller.requests.RegisterCandidateRequest;
import com.recruitmentbackend.recruitmentbackend.models.Candidate;
import com.recruitmentbackend.recruitmentbackend.models.JobOffer;
import com.recruitmentbackend.recruitmentbackend.services.CandidateService;
import com.recruitmentbackend.recruitmentbackend.services.JobOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.recruitmentbackend.recruitmentbackend.controller.AppConstants.API_MAPPING.*;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:27
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RestController
@RequestMapping(BASE_API + PUBLIC)
@RequiredArgsConstructor
public class PublicController {

    private final JobOfferService jobService;
    private final CandidateService candidateService;

    @GetMapping(JOB_OFFER)
    public ResponseEntity<List<JobOffer>> getJobOffer() {
        var result = jobService.getAllJobOffers();
        return ResponseEntity.ok(result);
    }


    @PostMapping(CREATE)
    public ResponseEntity<String> createCandidate(@RequestBody RegisterCandidateRequest registerCandidateRequest) {
        var result = candidateService.createCandidate(registerCandidateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //Denna ska ligga i Admin senare
    @GetMapping(CANDIDATES)
    public ResponseEntity<List<Candidate>> getCandidates() {
        var result = candidateService.getCandidates();
        return ResponseEntity.ok(result);
    }


}
