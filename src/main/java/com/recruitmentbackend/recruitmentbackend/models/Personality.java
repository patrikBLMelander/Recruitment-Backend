package com.recruitmentbackend.recruitmentbackend.models;

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
 * Time: 08:33
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "personality")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Personality(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
