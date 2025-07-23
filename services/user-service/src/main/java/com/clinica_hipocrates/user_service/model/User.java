package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class User {

    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "enabled")
    @EqualsAndHashCode.Include
    private Boolean enabled;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private UserType userType;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "lastname")
    @EqualsAndHashCode.Include
    private String lastname;

    @Column(name = "age")
    @EqualsAndHashCode.Include
    private Integer age;

    @Column(name = "dni", unique = true)
    @EqualsAndHashCode.Include
    private Integer dni;

    @Column(name = "email", unique = true)
    @EqualsAndHashCode.Include
    private String email;

    @Column(name = "profile_pic")
    @EqualsAndHashCode.Include
    private String profilePic;

    @Column(name = "health_insurance")
    @EqualsAndHashCode.Include
    private String healthInsurance;

    @Column(name = "profile_pic_alt")
    @EqualsAndHashCode.Include
    private String profilePicAlt;

    @ManyToMany
    @JoinTable(
            name = "specialist_speciality",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    @EqualsAndHashCode.Include
    private List<Speciality> specialities;


    public void setSpecificFields(User newUser) {
        if (newUser.getUserType() == this.getUserType()) {
            switch (this.getUserType()) {
                case ESPECIALISTA -> this.setSpecialities(newUser.getSpecialities());
                case PACIENTE -> {
                    this.setHealthInsurance(newUser.getHealthInsurance());
                    this.setProfilePicAlt(newUser.getProfilePicAlt());
                }
            }
        }
    }
}
