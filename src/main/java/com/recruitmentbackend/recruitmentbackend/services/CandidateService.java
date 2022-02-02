package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import com.recruitmentbackend.recruitmentbackend.controller.requests.*;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.models.DTO.CandidateDTO;
import com.recruitmentbackend.recruitmentbackend.repositories.*;
import com.recruitmentbackend.recruitmentbackend.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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
    private final JobOfferRepository jobRepo;
    private final CandidateRepository candidateRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final ServiceHelper serviceHelper;
    private final ExperienceRepository experienceRepo;
    private final EducationRepository educationRepo;
    private final CompetenceRepository competenceRepo;
    private final RecruitmentRepository recruitmentRepo;

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
        newCandidate.setEmail(request.getEmail().toLowerCase());
        newCandidate.setPassword(encoder.encode(request.getPassword()));
        newCandidate.setIsAdmin(false);
        newCandidate.setPhone(phone);
        newCandidate.setNickName(request.getNickName());

        newCandidate.setRoleList(List.of(role));

        serviceHelper.FillEmptyFields(newCandidate);

        candidateRepo.saveAndFlush(newCandidate);

        List<Personality> personalityList = serviceHelper.createPersonalityProfile(newCandidate);
        newCandidate.setPersonalityList(personalityList);


        final String msg = String.format("Candidate created: %s", newCandidate.getId());
        log.info(msg);
        return msg;
    }

    @Transactional
    public String createAdmin(CreateNewAdminRequest request) {
        System.out.println("Inne i serviceklassen");
        serviceHelper.checkIfEmailExists(request.getEmail());
        var role1 = roleRepo.getByName(Role.RoleConstant.ADMIN);
        var role2 = roleRepo.getByName(Role.RoleConstant.CANDIDATE);

        Candidate newCandidate = new Candidate();

        newCandidate.setFirstName(request.getFirstName());
        newCandidate.setLastName(request.getLastName());
        newCandidate.setEmail(request.getEmail());
        newCandidate.setPassword(encoder.encode(request.getPassword()));
        newCandidate.setRoleList(List.of(role1, role2));
        newCandidate.setIsAdmin(true);
        newCandidate.setPhone("");
        newCandidate.setNickName(1);

        serviceHelper.FillEmptyFields(newCandidate);

        candidateRepo.saveAndFlush(newCandidate);

        final String msg = String.format("Admin created: %s", newCandidate.getId());
        log.info(msg);


        return msg;
    }

    public String updatePassword(UpdatePasswordRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getUserId());

        if(serviceHelper.checkIfOldPasswordMatches(candidate, request.getOldPassword())){
            candidate.setPassword(encoder.encode(request.getNewPassword()));
            candidateRepo.saveAndFlush(candidate);
        }else {
            final String msg = "Old Password dont match with user password";
            log.info(msg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
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
    public String deleteExperience(DeleteRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());
        var experience = serviceHelper.getExperienceById(request.getToRemove());

        candidate.getExperienceList().remove(experience);
        candidateRepo.saveAndFlush(candidate);
        experienceRepo.delete(experience);

        final String msg = String.format("%s removed from user %s", experience.getTitle(), candidate.getId());
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
    public String deleteEducation(DeleteRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());
        var education = serviceHelper.getEducationById(request.getToRemove());

        candidate.getEducationList().remove(education);
        candidateRepo.saveAndFlush(candidate);
        educationRepo.delete(education);

        final String msg = String.format("%s removed from user %s", education.getTitle(), candidate.getId());
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
    @Transactional
    public String deleteCompetence(DeleteRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());
        var competence = serviceHelper.getCompetenceById(request.getToRemove());


        candidate.getCompetenciesList().remove(competence);
        candidateRepo.saveAndFlush(candidate);
        competenceRepo.delete(competence);

        final String msg = String.format("%s removed from user %s", competence.getName(), candidate.getId());
        log.info(msg);
        return msg;

    }


    public String setColorChoice(ColorChoiceRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());

        candidate.setColorChoice(request.getColorChoice());

        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("%s changed color schema", candidate.getId());
        log.info(msg);
        return msg;
    }

    public String setNicknameChoice(NicknamePresentationChoiceRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());

        candidate.setNickNameChoice(request.getNicknameChoice());
        candidateRepo.saveAndFlush(candidate);

        final String msg = String.format("%s changed how nickname is presented", candidate.getId());
        log.info(msg);
        return msg;
    }

    public CandidateDTO getUserInfo(CandidateDetails request) {
        var candidate = serviceHelper.getCandidateByEmail(request.getEmail());

        return new CandidateDTO(
                candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getNickName(),
                candidate.getEmail(),
                candidate.getPresentation(),
                candidate.getIsAdmin(),
                candidate.getColorChoice(),
                candidate.getNickNameChoice(),
                candidate.getRoleList(),
                candidate.getExperienceList(),
                candidate.getEducationList(),
                candidate.getCompetenciesList(),
                candidate.getPersonalityList());
    }

    @Transactional
    public String deleteCandidate(DeleteRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());
        System.out.println("in deleteCandidate");

        List<JobOffer> allJobOffers= jobRepo.findAll();
        for (JobOffer jb : allJobOffers) {
            for (Recruitment r : jb.getRecruitmentList()) {
                System.out.println("in loop");
                r.removeCandidate(candidate);
                recruitmentRepo.saveAndFlush(r);
            }
        }
        candidateRepo.delete(candidate);
        final String msg = String.format("%s is removed from database", request.getCandidateId());
        log.info(msg);
        return msg;

    }

    public String deleteAdmin(DeleteRequest request) {
        var candidate = serviceHelper.getCandidateById(request.getCandidateId());
        candidateRepo.delete(candidate);

        final String msg = String.format("%s is removed from database", request.getCandidateId());
        log.info(msg);
        return msg;

    }



}
