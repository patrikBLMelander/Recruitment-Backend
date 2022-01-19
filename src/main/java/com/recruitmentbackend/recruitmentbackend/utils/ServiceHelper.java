package com.recruitmentbackend.recruitmentbackend.utils;

import com.recruitmentbackend.recruitmentbackend.controller.requests.CreateNewJobOfferRequest;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.CompetenceRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.JobOfferRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 15:21
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceHelper {
    private final CandidateRepository candidateRepo;
    private final CompetenceRepository competenceRepo;
    private final RecruitmentRepository recruitmentRepo;
    private final JobOfferRepository jobRepo;

    public void checkIfEmailExists(String email) {
        if (candidateRepo.existsByEmail(email)) {
            final String msg = "Email already in use";
            log.info("Trying to create Candidate, failed due to {}", msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public String formatPhoneForStorage(String phone) {
        final String formatNumber = phone.replaceAll("\\D", "").trim();
        checkIfPhoneExists(formatNumber);
        return formatNumber;
    }

    private void checkIfPhoneExists(String phone) {
        if (candidateRepo.existsByPhone(phone)) {
            final String msg = "Phone number already in use";
            log.info("Trying to create Candidate, failed due to {}", msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public List<Personality> createPersonalityProfile( ) {
        Personality p1 = new Personality("Openness", 50);
        Personality p2 = new Personality("Conscientiousness", 50);
        Personality p3 = new Personality("Extroversion", 50);
        Personality p4 = new Personality("Agreeableness", 50);
        Personality p5 = new Personality("Neuroticism", 50);
        List<Personality> listToReturn = new ArrayList<>();
        listToReturn.add(p1);
        listToReturn.add(p2);
        listToReturn.add(p3);
        listToReturn.add(p4);
        listToReturn.add(p5);

        return listToReturn;
    }
    public List<Recruitment> defaultRecruitmentStepsList(JobOffer joboffer){
        List<Recruitment> listToReturn = new ArrayList<>();
        Recruitment apply = new Recruitment("apply", joboffer);
        recruitmentRepo.saveAndFlush(apply);
        Recruitment interesting = new Recruitment("interesting", joboffer);
        recruitmentRepo.saveAndFlush(interesting);
        Recruitment interview = new Recruitment("interview", joboffer);
        recruitmentRepo.saveAndFlush(interview);
        Recruitment dismiss = new Recruitment("dismiss", joboffer);
        recruitmentRepo.saveAndFlush(dismiss);
        Recruitment hire = new Recruitment("hire", joboffer);
        recruitmentRepo.saveAndFlush(hire);
        listToReturn.add(apply);
        listToReturn.add(interesting);
        listToReturn.add(interview);
        listToReturn.add(dismiss);
        listToReturn.add(hire);
        return listToReturn;
    }

    public List<Competence> fillCompetenceList(CreateNewJobOfferRequest request, JobOffer newJobOffer) {
        List<Competence> listToReturn = new ArrayList<>();

        for (int i = 0; i < request.getCompetenceList().size(); i++) {
            Competence newCompetence = new Competence();
            newCompetence.setName(request.getCompetenceList().get(i).getName());
            newCompetence.setValue(request.getCompetenceList().get(i).getValue());
            newCompetence.setJobOffer(newJobOffer);
            competenceRepo.saveAndFlush(newCompetence);
            listToReturn.add(newCompetence);
        }


        return listToReturn;
    }

    public Candidate getCandidateById(UUID id) {
        var candidate = candidateRepo.findById(id);
        if (candidate.isPresent()) {
            log.info("Fetching user");
            return candidate.get();
        }
        final String msg = String.format("No candidate found with id %s", id);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);

    }

    public JobOffer getJobOfferById(UUID id) {
        var jobOffer = jobRepo.findById(id);
        if (jobOffer.isPresent()) {
            log.info("Fetching user");
            return jobOffer.get();
        }
        final String msg = String.format("No jobOffer found with id %s", id);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);

    }

    public boolean alreadyApplied(Candidate candidate, JobOffer jobOffer) {

        for (Recruitment r : jobOffer.getRecruitmentList()) {
            for (Candidate c: r.getCandidateList()) {
                if(c.getId() == candidate.getId()){
                    return true;
                }
            }
        }
        return false;
    }
}
