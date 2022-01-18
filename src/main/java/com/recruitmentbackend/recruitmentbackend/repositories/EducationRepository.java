package com.recruitmentbackend.recruitmentbackend.repositories;

import com.recruitmentbackend.recruitmentbackend.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:06
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
public interface EducationRepository extends JpaRepository<Education, UUID> {
}
