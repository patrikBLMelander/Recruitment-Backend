package com.recruitmentbackend.recruitmentbackend.models;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 10:35
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "recruitment_step")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecruitmentStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "recruitment_step")
            private List<Candidate> candidateList;
}
