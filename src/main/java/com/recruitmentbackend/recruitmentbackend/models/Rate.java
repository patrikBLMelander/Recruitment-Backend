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
 * Time: 08:24
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@Entity
@Table(name = "rate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "count", nullable = false)
    private Integer count;
}
