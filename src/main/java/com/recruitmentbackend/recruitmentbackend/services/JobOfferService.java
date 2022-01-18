package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.models.JobOffer;
import com.recruitmentbackend.recruitmentbackend.repositories.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 15:04
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JobOfferService {

    private final JobOfferRepository jobRepo;

    public List<JobOffer> getAllJobOffers() {
        log.info("Fetching all jobOffers");
        return jobRepo.findAll();
    }
}
