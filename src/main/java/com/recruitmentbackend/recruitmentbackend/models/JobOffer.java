package com.recruitmentbackend.recruitmentbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @Column(name = "title")
    private String title;
    @Column(name = "img_url")
    private String imageUrl;
    @Column(name = "publish_date")
    private LocalDate publishDate;
    @Column(name = "apply_date")
    private LocalDate applyDate;
    @Column(name = "preview", length = 2000)
    private String preview;
    @Column(name = "company_description", length = 2000)
    private String companyDescription;
    @Column(name = "about_role", length = 2000)
    private String aboutRole;
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "jobOffer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Recruitment> recruitmentList = new ArrayList<>();

    @OneToMany(mappedBy = "jobOffer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Competence> competenceList = new ArrayList<>();

    public void removeRecruitment (Recruitment recruitment){
        if(recruitmentList.contains(recruitment)){
            this.recruitmentList.remove(recruitment);
        }
    }

}
