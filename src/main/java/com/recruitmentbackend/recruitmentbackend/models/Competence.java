package com.recruitmentbackend.recruitmentbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 08:37
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "competence")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "jobOffer_id")
    private JobOffer jobOffer;

}
