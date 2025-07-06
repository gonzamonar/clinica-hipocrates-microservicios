package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ESPECIALISTA")
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Specialist extends User {

    @ManyToMany
    @JoinTable(
            name = "specialist_speciality",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    @EqualsAndHashCode.Include
    private List<Speciality> specialities = new ArrayList<>();


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
}
