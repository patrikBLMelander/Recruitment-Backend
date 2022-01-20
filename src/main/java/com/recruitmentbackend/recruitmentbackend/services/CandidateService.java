package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import com.recruitmentbackend.recruitmentbackend.controller.requests.*;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.repositories.*;
import com.recruitmentbackend.recruitmentbackend.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 15:09
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class CandidateService {
    private final CandidateRepository candidateRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final ServiceHelper serviceHelper;
    private final ExperienceRepository experienceRepo;
    private final EducationRepository educationRepo;
    private final CompetenceRepository competenceRepo;

    public List<Candidate> getCandidates() {
        log.info("Fetching all candidates");
        return candidateRepo.findAll();
    }

    @Transactional
    public String createCandidate(RegisterCandidateRequest request) {
        serviceHelper.checkIfEmailExists(request.getEmail());
        final String phone = serviceHelper.formatPhoneForStorage(request.getPhone());
        var role = roleRepo.getByName(Role.RoleConstant.CANDIDATE);

        Candidate newCandidate = new Candidate();

        newCandidate.setFirstName(request.getFirstName());
        newCandidate.setLastName(request.getLastName());
        newCandidate.setEmail(request.getEmail());
        newCandidate.setPassword(encoder.encode(request.getPassword()));

        newCandidate.setPhone(phone);
        newCandidate.setNickName(request.getNickName());

        newCandidate.setRoleList(List.of(role));

        FillEmptyFields(newCandidate);

        candidateRepo.saveAndFlush(newCandidate);

        List<Personality> personalityList = serviceHelper.createPersonalityProfile(newCandidate);
        newCandidate.setPersonalityList(personalityList);


        final String msg = String.format("Candidate created: %s", newCandidate.getId());
        log.info(msg);
        return msg;
    }

    @Transactional
    public String createAdmin(CreateNewAdminRequest request) {
        serviceHelper.checkIfEmailExists(request.getEmail());
        var role = roleRepo.getByName(Role.RoleConstant.ADMIN);

        Candidate newCandidate = new Candidate();

        newCandidate.setFirstName(request.getFirstName());
        newCandidate.setLastName(request.getLastName());
        newCandidate.setEmail(request.getEmail());
        newCandidate.setPassword(encoder.encode(request.getPassword()));
        newCandidate.setRoleList(List.of(role));

        newCandidate.setPhone("");
        newCandidate.setNickName(1);

        FillEmptyFields(newCandidate);

        candidateRepo.saveAndFlush(newCandidate);

        final String msg = String.format("Admin created: %s", newCandidate.getId());
        log.info(msg);


        return msg;
    }

    public String updatePassword(UpdatePasswordRequest request) {
        var candidate = serviceHelper.getCandidateByEmail(request.getUserName());

        if(checkIfOldPasswordMatches(candidate, request.getOldPassword())){
            candidate.setPassword(request.getNewPassword());
            candidateRepo.saveAndFlush(candidate);
        }else {
            final String msg = "Old Password dont match with user password";
            log.info(msg);
            return msg;
        }

        final String msg = String.format("Candidate %s successfully updated the password ", candidate.getId());
        log.info(msg);
        return msg;
    }


    @Transactional
    public String updatePresentation(UpdatePresentationRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());
        candidate.setPresentation(request.getPresentation());

        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("Presentation updated on user %s", candidate.getId());
        log.info(msg);
        return msg;
    }

    @Transactional
    public String updatePersonality(UpdatePersonalityRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());

        candidate.getPersonalityList().get(0).setValue(request.getOpenness());
        candidate.getPersonalityList().get(1).setValue(request.getConscientiousness());
        candidate.getPersonalityList().get(2).setValue(request.getExtroversion());
        candidate.getPersonalityList().get(3).setValue(request.getAgreeableness());
        candidate.getPersonalityList().get(4).setValue(request.getNeuroticism());


        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("Personality updated on user %s", candidate.getId());
        log.info(msg);
        return msg;
    }

    @Transactional
    public String addExperience(AddExperienceRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());
        Experience newExperience = new Experience();
        newExperience.setCandidate(candidate);
        newExperience.setTitle(request.getTitle());
        newExperience.setDescription(request.getDescription());
        newExperience.setStartDate(request.getStartDate());
        newExperience.setEndDate(request.getEndDate());

        experienceRepo.saveAndFlush(newExperience);

        candidate.getExperienceList().add(newExperience);

        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("Experience added to user %s", candidate.getId());
        log.info(msg);
        return msg;
    }
    @Transactional
    public String addEducation(AddEducationRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());
        Education newEducation = new Education();
        newEducation.setCandidate(candidate);
        newEducation.setTitle(request.getTitle());
        newEducation.setDescription(request.getDescription());
        newEducation.setStartDate(request.getStartDate());
        newEducation.setEndDate(request.getEndDate());

        educationRepo.saveAndFlush(newEducation);

        candidate.getEducationList().add(newEducation);

        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("Education added to user %s", candidate.getId());
        log.info(msg);
        return msg;
    }
    @Transactional
    public String addCompetence(AddCompetenceRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());

        Competence newCompetence = new Competence();
        newCompetence.setName(request.getName());
        newCompetence.setValue(request.getValue());
        newCompetence.setCandidate(candidate);

        competenceRepo.saveAndFlush(newCompetence);

        candidate.getCompetenciesList().add(newCompetence);
        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("%s added to user %s", newCompetence.getName(), candidate.getId());
        log.info(msg);
        return msg;
    }

    private void FillEmptyFields(Candidate newCandidate) {
        newCandidate.setPresentation("");
        newCandidate.setExperienceList(new ArrayList<>());
        newCandidate.setEducationList(new ArrayList<>());
        newCandidate.setCompetenciesList(new ArrayList<>());
        newCandidate.setColorChoice("default");
        newCandidate.setIsAdmin(false);
        newCandidate.setRates(new ArrayList<>());
    }

    public boolean checkIfOldPasswordMatches(Candidate candidate, String passwordToCheck) {
        return encoder.matches(passwordToCheck, candidate.getPassword());
    }

}
