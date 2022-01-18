package com.recruitmentbackend.recruitmentbackend.config.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Table(name = "role")
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    private RoleConstant name;

    public Role(RoleConstant name) {
        this.name = name;
    }

    public enum RoleConstant {
        SUPER_ADMIN,
        ADMIN,
        CANDIDATE
    }
}
