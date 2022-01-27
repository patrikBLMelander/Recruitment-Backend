package com.recruitmentbackend.recruitmentbackend.models.DTO;

import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-25
 * Time: 15:21
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Value
@Getter
@AllArgsConstructor
public class CandidateDTO {
     UUID id;
     String firstName;
     String lastName;
     int nickName;
     String email;
     String presentation;
     Boolean isAdmin;
     String colorChoice;
     String nickNameChoice;
     List<Role> roleList;
     List<Experience> experienceList;
     List<Education> educationList;
     List<Competence> competenciesList;
     List<Personality> personalityList;

}
