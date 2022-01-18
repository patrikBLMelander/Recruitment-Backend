package com.recruitmentbackend.recruitmentbackend.models;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 10:34
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "location", nullable = false)
    private String location;
}
