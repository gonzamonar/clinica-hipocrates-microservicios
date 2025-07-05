package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("PACIENTE")
public class Patient extends User {
    private String healthInsurance;
    private String profilePicAlt;

    public Patient() {
    }

    private Patient(PatientBuilder builder) {
        super(builder);
        this.healthInsurance = builder.healthInsurance;
        this.profilePicAlt = builder.profilePicAlt;
    }

    public static PatientBuilder builder() {
        return new PatientBuilder();
    }

    @Override
    public void setSpecificFields(User user) {
        this.healthInsurance = ((Patient) user).healthInsurance;
        this.profilePicAlt = ((Patient) user).profilePicAlt;
    }

    public static class PatientBuilder extends UserBuilder<Patient, PatientBuilder> {
        private String healthInsurance;
        private String profilePicAlt;

        public PatientBuilder healthInsurance(String healthInsurance) {
            this.healthInsurance = healthInsurance;
            return this;
        }

        public PatientBuilder profilePicAlt(String profilePicAlt) {
            this.profilePicAlt = profilePicAlt;
            return this;
        }

        @Override
        protected PatientBuilder self() {
            return this;
        }

        @Override
        public Patient build() {
            return new Patient(this);
        }
    }

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public String getProfilePicAlt() {
        return profilePicAlt;
    }

    public void setProfilePicAlt(String profilePicAlt) {
        this.profilePicAlt = profilePicAlt;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "healthInsurance='" + healthInsurance + '\'' +
                ", profilePicAlt='" + profilePicAlt + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(healthInsurance, patient.healthInsurance) && Objects.equals(profilePicAlt, patient.profilePicAlt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), healthInsurance, profilePicAlt);
    }
}
