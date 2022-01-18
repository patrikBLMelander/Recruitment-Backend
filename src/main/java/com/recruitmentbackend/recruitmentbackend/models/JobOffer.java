package com.recruitmentbackend.recruitmentbackend.models;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 10:33
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "jobOffer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Slf4j
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;
    @Column(name = "apply_date")
    private LocalDate applyDate;
    @Column(name = "preview")
    private String preview;
    @Column(name = "company_description")
    private String companyDescription;
    @Column(name = "about_role", nullable = false)
    private String aboutRole;

    @OneToMany(mappedBy = "jobOffer")
    private List<RecruitmentStep> recruitmentStepList;

    @OneToMany(mappedBy = "jobOffer")
    private List<Competence> competenceList;

    @ManyToOne
    @JoinColumn(name = "location_ID")
    private Location location;
}
