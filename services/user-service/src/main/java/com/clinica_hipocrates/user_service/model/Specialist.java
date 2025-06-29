package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("ESPECIALISTA")
public class Specialist extends User {

    @ManyToMany
    @JoinTable(
            name = "specialist_speciality",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private List<Speciality> specialities = new ArrayList<>();

    public Specialist() {
    }

    public Specialist(Long id, UserType userType, String name, String lastname, Integer age, Integer dni, String email, String profilePic, List<Speciality> specialities) {
        super(id, userType, name, lastname, age, dni, email, profilePic);
        this.specialities = specialities;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    @Override
    public String toString() {
        return "Specialist{" +
                "specialities=" + specialities +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Specialist that = (Specialist) o;
        return Objects.equals(specialities, that.specialities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialities);
    }
}
