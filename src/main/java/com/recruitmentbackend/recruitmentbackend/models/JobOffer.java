package com.recruitmentbackend.recruitmentbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
@Table(name = "job_offer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private List<Recruitment> recruitmentList = defaultRecruitmentStepsList();

    @OneToMany(mappedBy = "jobOffer")
    private List<Competence> competenceList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public List<Recruitment> defaultRecruitmentStepsList(){
        List<Recruitment> listToReturn = new ArrayList<>();
        Recruitment apply = new Recruitment("apply");
        Recruitment interesting = new Recruitment("interesting");
        Recruitment interview = new Recruitment("interview");
        Recruitment dismiss = new Recruitment("dismiss");
        Recruitment hire = new Recruitment("hire");
        listToReturn.add(apply);
        listToReturn.add(interesting);
        listToReturn.add(interview);
        listToReturn.add(dismiss);
        listToReturn.add(hire);
        return listToReturn;
    }
}
