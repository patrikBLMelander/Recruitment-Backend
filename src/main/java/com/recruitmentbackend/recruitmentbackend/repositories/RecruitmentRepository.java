package com.recruitmentbackend.recruitmentbackend.repositories;

import com.recruitmentbackend.recruitmentbackend.models.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:08
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {
}
