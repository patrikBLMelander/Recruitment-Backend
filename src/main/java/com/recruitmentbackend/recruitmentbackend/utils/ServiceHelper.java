package com.recruitmentbackend.recruitmentbackend.utils;

import com.recruitmentbackend.recruitmentbackend.models.Personality;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
}
