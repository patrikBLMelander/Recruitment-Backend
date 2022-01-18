package com.recruitmentbackend.recruitmentbackend.models;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
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
@Table(name = "recruitment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "recruitment")
            private List<Candidate> candidateList;

    @ManyToOne
    @JoinColumn(name = "jobOffer_id")
    private JobOffer jobOffer;

    public Recruitment(String title) {
        this.title = title;
        List<Candidate> candidateList = new ArrayList<>();
    }


}
