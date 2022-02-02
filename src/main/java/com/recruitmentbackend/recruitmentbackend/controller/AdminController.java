package com.recruitmentbackend.recruitmentbackend.controller;
import com.recruitmentbackend.recruitmentbackend.controller.requests.*;
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
@RequestMapping(BASE_API + ADMIN)
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final CandidateService candidateService;
    private final JobOfferService jobOfferService;



    @PostMapping(CREATE)
    public ResponseEntity<String> createAdmin(@RequestBody CreateNewAdminRequest createNewAdminRequest) {
        var result = candidateService.createAdmin(createNewAdminRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @PostMapping(JOB_OFFER+CREATE)
    public ResponseEntity<String> createJobOffer(@RequestBody CreateNewJobOfferRequest createNewJobOfferRequest) {
        var result = jobOfferService.createJobOffer(createNewJobOfferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(UPDATE+RATE)
    public ResponseEntity<?> updateRate(@RequestBody SetRateOnCandidateRequest request){
        var result = jobOfferService.setNewRate(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @PutMapping(UPDATE+NICKNAME)
    public ResponseEntity<?> setNicknameChoice(@RequestBody NicknamePresentationChoiceRequest request){
        var result = candidateService.setNicknameChoice(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @PostMapping(JOB_OFFER)
    public ResponseEntity<JobOffer> getJobOfferDetails(@RequestBody ApplyForJobRequest request){
        var result = jobOfferService.getOneJobOffer(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping(JOB_OFFER+UPDATE)
    public ResponseEntity<?> updateRecruitmentStepOrder(@RequestBody ChangeRecruitmentIndex request){
        var result = jobOfferService.updateRecruitmentStepsOrder(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @PutMapping(JOB_OFFER+DELETE)
    public ResponseEntity<?> removeJobOffer(@RequestBody ChangeRecruitmentIndex request){
        var result = jobOfferService.updateRecruitmentStepsOrder(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }


    @PostMapping(RECRUITMENT+UPDATE)
    public ResponseEntity<?> addRecruitmentStep(@RequestBody AddRecruitmentsRequest request){
        var result = jobOfferService.addRecruitmentStep(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
    @PostMapping(RECRUITMENT+DELETE)
    public ResponseEntity<?> addRecruitmentStep(@RequestBody RemoveRecruitmentsRequest request){
        var result = jobOfferService.deleteRecruitmentStep(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @PutMapping(RECRUITMENT+CANDIDATES)
    public ResponseEntity<?> changListForCandidate(@RequestBody ChangListForCandidateRequest request){
        var result = jobOfferService.changListForCandidate(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<?> deleteCandidate(@RequestBody DeleteRequest request) {
        var result = candidateService.deleteCandidate(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @GetMapping(CANDIDATES)
    public ResponseEntity<List<Candidate>> getCandidates() {
        var result = candidateService.getCandidates();
        return ResponseEntity.ok(result);
    }

}
