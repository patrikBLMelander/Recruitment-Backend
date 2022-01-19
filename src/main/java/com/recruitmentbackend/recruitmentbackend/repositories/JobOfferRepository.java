package com.recruitmentbackend.recruitmentbackend.repositories;

import com.recruitmentbackend.recruitmentbackend.models.Experience;
import com.recruitmentbackend.recruitmentbackend.models.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:07
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {

}
