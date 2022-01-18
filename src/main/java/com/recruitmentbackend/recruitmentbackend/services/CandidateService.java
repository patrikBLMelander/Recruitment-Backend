package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import com.recruitmentbackend.recruitmentbackend.controller.requests.RegisterCandidateRequest;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.RoleRepository;
import com.recruitmentbackend.recruitmentbackend.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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

        newCandidate.setPresentation("");
        newCandidate.setExperienceList(new ArrayList<Experience>());
        newCandidate.setEducationList(new ArrayList<Education>());
        newCandidate.setCompetenciesList(new ArrayList<Competence>());
        newCandidate.setColorChoice("default");
        newCandidate.setIsAdmin(false);
        newCandidate.setPersonalityList(serviceHelper.createPersonalityProfile());
        newCandidate.setRates(new ArrayList<Rate>());

        candidateRepo.saveAndFlush(newCandidate);

        final String msg = String.format("Candidate created: %s", newCandidate.getId());
        log.info(msg);
        return msg;
    }



}
