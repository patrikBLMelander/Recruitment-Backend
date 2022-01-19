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


    public String createCandidate(RegisterCandidateRequest request) {
        serviceHelper.checkIfEmailExists(request.getEmail());
        final String phone = serviceHelper.formatPhoneForStorage(request.getPhone());
        var role = roleRepo.getByName(Role.RoleConstant.CANDIDATE);

        Candidate newCandidate = new Candidate();

        newCandidate.setFirstName(request.getFirstName());
        newCandidate.setLastName(request.getLastName());
        newCandidate.setEmail(request.getEmail());
        newCandidate.setPassword(encoder.encode(request.getPassword()));

        newCandidate.setPhone(request.getPhone());
        newCandidate.setNickName(request.getNickName());

        newCandidate.setRoleList(List.of(role));

        FillEmptyFields(newCandidate);

        candidateRepo.saveAndFlush(newCandidate);

        final String msg = String.format("Candidate created: %s", newCandidate.getId());
        log.info(msg);
        return msg;
    }


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

    public String updatePresentation(UpdatePresentationRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());
        candidate.setPresentation(request.getPresentation());

        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("Presentation updated on user %s", candidate.getId());
        log.info(msg);
        return msg;
    }

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
        newCandidate.setExperienceList(new ArrayList<Experience>());
        newCandidate.setEducationList(new ArrayList<Education>());
        newCandidate.setCompetenciesList(new ArrayList<Competence>());
        newCandidate.setColorChoice("default");
        newCandidate.setIsAdmin(false);
        newCandidate.setPersonalityList(serviceHelper.createPersonalityProfile());
        newCandidate.setRates(new ArrayList<Rate>());
    }



}
