package com.clinica_hipocrates.appointment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cached_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CachedUser {

    @Id
    @EqualsAndHashCode.Exclude
    @Column(name = "id")
    private Long id;

    @Column(name = "user_type")
    protected String userType;

    @Column(name = "name")
    protected String name;

    @Column(name = "lastname")
    protected String lastname;

    @ElementCollection
    @CollectionTable(name = "cached_user_speciality_ids", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "speciality_id")
    private Set<Long> specialityIds = new HashSet<>();

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "enabled")
    private Boolean enabled;
}
