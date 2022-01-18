package com.recruitmentbackend.recruitmentbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value", nullable = false)
    private Integer value;
}
