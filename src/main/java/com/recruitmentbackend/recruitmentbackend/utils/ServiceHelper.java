package com.recruitmentbackend.recruitmentbackend.utils;

import com.recruitmentbackend.recruitmentbackend.controller.requests.CreateNewJobOfferRequest;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.repositories.*;
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
    private final PersonalityRepository personalityRepo;
    private final EducationRepository educationRepo;
    private final ExperienceRepository experienceRepo;

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

    public List<Personality> createPersonalityProfile(Candidate candidate) {
        List<Personality> listToReturn = new ArrayList<>();
        Personality p1 = new Personality("Openness", 50, candidate);
        personalityRepo.saveAndFlush(p1);
        Personality p2 = new Personality("Conscientiousness", 50, candidate);
        personalityRepo.saveAndFlush(p2);
        Personality p3 = new Personality("Extroversion", 50, candidate);
        personalityRepo.saveAndFlush(p3);
        Personality p4 = new Personality("Agreeableness", 50, candidate);
        personalityRepo.saveAndFlush(p4);
        Personality p5 = new Personality("Neuroticism", 50, candidate);
        personalityRepo.saveAndFlush(p5);
        listToReturn.add(p1);
        listToReturn.add(p2);
        listToReturn.add(p3);
        listToReturn.add(p4);
        listToReturn.add(p5);
        log.info("new personality profile created on {}", candidate.getId() );
        return listToReturn;
    }
    public List<Recruitment> defaultRecruitmentStepsList(JobOffer joboffer){
        List<Recruitment> listToReturn = new ArrayList<>();
        Recruitment apply = new Recruitment(0,"apply", joboffer);
        recruitmentRepo.saveAndFlush(apply);
        Recruitment interesting = new Recruitment(1,"interesting", joboffer);
        recruitmentRepo.saveAndFlush(interesting);
        Recruitment interview = new Recruitment(2,"interview", joboffer);
        recruitmentRepo.saveAndFlush(interview);
        Recruitment dismiss = new Recruitment(3,"dismiss", joboffer);
        recruitmentRepo.saveAndFlush(dismiss);
        Recruitment hire = new Recruitment(4,"hire", joboffer);
        recruitmentRepo.saveAndFlush(hire);
        listToReturn.add(apply);
        listToReturn.add(interesting);
        listToReturn.add(interview);
        listToReturn.add(dismiss);
        listToReturn.add(hire);

        log.info("new default recruitment process created on {}", joboffer.getId() );
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

        log.info("filled competencies on {}", newJobOffer.getId() );
        return listToReturn;
    }

    public Candidate getCandidateById(UUID id) {
        var candidate = candidateRepo.findById(id);
        if (candidate.isPresent()) {
            log.info("Fetching user by UUID id");
            return candidate.get();
        }
        final String msg = String.format("No candidate found with id %s", id);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public Candidate getCandidateById(String id) {
        var candidate = candidateRepo.findById(UUID.fromString(id));
        if (candidate.isPresent()) {
            log.info("Fetching user by String id");
            return candidate.get();
        }
        final String msg = String.format("No candidate found with id %s", id);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public Recruitment getRecruitmentById(UUID id) {
        var recruitment = recruitmentRepo.findById(id);
        if (recruitment.isPresent()) {
            log.info("Fetching recruitment UUID id");
            return recruitment.get();
        }
        final String msg = String.format("No recruitment found with id %s", id);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public Candidate getCandidateByEmail(String email) {
        var candidate = candidateRepo.findByEmail(email);
        if (candidate.isPresent()) {
            log.info("Fetching user by email");
            return candidate.get();
        }
        final String msg = String.format("No candidate found with id %s", email);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }


    public JobOffer getJobOfferById(UUID id) {
        var jobOffer = jobRepo.findById(id);
        if (jobOffer.isPresent()) {
            log.info("Fetching jobOffer by UUID");
            return jobOffer.get();
        }
        final String msg = String.format("No jobOffer found with id %s", id);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);

    }
    public Competence getCompetenceById(UUID toRemove) {
        var competence = competenceRepo.findById(toRemove);
        if (competence.isPresent()) {
            log.info("Fetching Competence to remove by UUID");
            return competence.get();
        }
        final String msg = String.format("No competence found with id %s", toRemove);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
    public Education getEducationById(UUID toRemove) {
        var education = educationRepo.findById(toRemove);
        if (education.isPresent()) {
            log.info("Fetching Education to remove by UUID");
            return education.get();
        }
        final String msg = String.format("No education found with id %s", toRemove);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
    public Experience getExperienceById(UUID toRemove) {
        var experience = experienceRepo.findById(toRemove);
        if (experience.isPresent()) {
            log.info("Fetching Experience to remove by UUID");
            return experience.get();
        }
        final String msg = String.format("No experience found with id %s", toRemove);
        log.info(msg);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public boolean alreadyApplied(Candidate candidate, JobOffer jobOffer) {

        for (Recruitment r : jobOffer.getRecruitmentList()) {
            for (Candidate c: r.getCandidateList()) {
                if(c.getId() == candidate.getId()){
                    log.info("Candidate {} already applyed to {}",candidate.getId() , jobOffer.getId() );
                    return true;
                }
            }
        }
        log.info("Candidate {} can apply to {}",candidate.getId() , jobOffer.getId());
        return false;
    }
    public Integer getTotalAmountOfCandidates(JobOffer jobOffer) {
        int totalAmount = 0;
        for (Recruitment r : jobOffer.getRecruitmentList()) {
            totalAmount += r.getCandidateList().size();
        }
        log.info("Total candidates in {} are {}",jobOffer.getId(), totalAmount );
        return totalAmount;
    }

    public Integer getAmountOfNewCandidates(JobOffer jobOffer) {
        int newCandidates = 0;
        for (Recruitment r : jobOffer.getRecruitmentList()) {
            if (r.getIndex()==0){
                return newCandidates += r.getCandidateList().size();
            }
        }
        log.info("New candidates in {} are {}",jobOffer.getId(), newCandidates);
        return newCandidates;

    }
}
