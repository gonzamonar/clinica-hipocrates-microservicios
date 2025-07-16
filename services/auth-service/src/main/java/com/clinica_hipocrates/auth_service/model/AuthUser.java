package com.clinica_hipocrates.auth_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_users")
public class AuthUser {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "alias", unique = true, nullable = false)
    private Long alias;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "verified")
    private Boolean verified;
}
