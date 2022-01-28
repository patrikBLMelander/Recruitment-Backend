package com.recruitmentbackend.recruitmentbackend.config;

import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import com.recruitmentbackend.recruitmentbackend.models.Candidate;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
public class BootstrapConfig {

    @Bean
    public CommandLineRunner bootstrap(CandidateRepository candidateRepo,
                                       RoleRepository roleRepo,
                                       PasswordEncoder encoder) {
        return (args -> {
            if (!candidateRepo.existsByNickName(1)) {
                var superAdminRole = roleRepo.saveAndFlush(new Role(Role.RoleConstant.SUPER_ADMIN));
                var adminRole = roleRepo.saveAndFlush(new Role(Role.RoleConstant.ADMIN));
                var candidateRole = roleRepo.saveAndFlush(new Role(Role.RoleConstant.CANDIDATE));

                Candidate admin = new Candidate();
                    admin.setNickName(1);
                    admin.setFirstName("Patrik");
                    admin.setLastName("Melander");
                    admin.setEmail("admin@email.com");
                    admin.setRoleList(List.of(superAdminRole, adminRole, candidateRole));
                    admin.setPassword(encoder.encode("admin1234"));
                    admin.setIsAdmin(true);
                    admin.setNickNameChoice("default");
                    admin.setColorChoice("teal");

                candidateRepo.saveAndFlush(admin);

                log.info("Added SUPER_ADMIN to the application {}", admin);
            }
        });
    }
}
