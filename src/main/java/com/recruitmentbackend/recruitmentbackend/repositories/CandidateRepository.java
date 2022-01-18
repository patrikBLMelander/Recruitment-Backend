package com.recruitmentbackend.recruitmentbackend.repositories;

import com.recruitmentbackend.recruitmentbackend.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 09:24
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    Optional<Candidate> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByNickName(int nickName);
    Boolean existsByPhone(String phone);
}
