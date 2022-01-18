package com.recruitmentbackend.recruitmentbackend.repositories;

import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 09:06
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Role getByName(Role.RoleConstant name);
}
