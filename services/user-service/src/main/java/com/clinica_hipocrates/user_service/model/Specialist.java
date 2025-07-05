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

    @Override
    public void setSpecificFields(User user) {
        this.specialities = ((Specialist) user).specialities;
    }

    private Specialist(SpecialistBuilder builder) {
        super(builder);
        this.specialities = builder.specialities;
    }

    public static SpecialistBuilder builder() {
        return new SpecialistBuilder();
    }

    public static class SpecialistBuilder extends UserBuilder<Specialist, SpecialistBuilder> {
        private List<Speciality> specialities = new ArrayList<>();

        public SpecialistBuilder specialities(List<Speciality> specialities) {
            this.specialities = specialities;
            return this;
        }

        @Override
        protected SpecialistBuilder self() {
            return this;
        }

        @Override
        public Specialist build() {
            return new Specialist(this);
        }
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
