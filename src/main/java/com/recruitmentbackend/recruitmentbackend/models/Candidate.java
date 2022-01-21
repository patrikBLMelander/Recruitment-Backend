package com.recruitmentbackend.recruitmentbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recruitmentbackend.recruitmentbackend.config.security.Role;
import com.recruitmentbackend.recruitmentbackend.repositories.RoleRepository;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 08:39
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "candidate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "nick_name")
    private int nickName;
    @Column(name = "first_name", length = 200, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 200, nullable = false)
    private String lastName;
    @Column(name = "phone", length = 15)
    private String phone;
    @Column(name = "email", length = 325, unique = true, nullable = false)
    private String email;
    @Column(name = "password", length = 100, nullable = false)
    private String password;
    @Column(name = "presentation", length = 2000)
    private String presentation;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Column(name = "color_choice", length = 30)
    private String colorChoice;
    @Column(name = "nick_name_choice", length = 30)
    private String nickNameChoice;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList;

    @OneToMany(mappedBy = "candidate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rate> rates = new ArrayList<>();

    @OneToMany(mappedBy = "candidate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Experience> experienceList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Education> educationList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Competence> competenciesList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Personality> personalityList  = new ArrayList<>();

    public void addRoleToUser(String roleName, RoleRepository roleRepository) {
        if(this.roleList == null) {
            this.roleList = new ArrayList<>();
        }

    }

}
