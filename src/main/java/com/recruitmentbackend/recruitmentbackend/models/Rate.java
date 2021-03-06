package com.recruitmentbackend.recruitmentbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Time: 08:24
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "rate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "job_offer_id", nullable = false)
    private String jobOfferId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;


}
