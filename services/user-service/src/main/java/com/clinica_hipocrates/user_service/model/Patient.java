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

    public Patient(Long id, UserType userType, String name, String lastname, Integer age, Integer dni, String email, String profilePic, String healthInsurance, String profilePicAlt) {
        super(id, userType, name, lastname, age, dni, email, profilePic);
        this.healthInsurance = healthInsurance;
        this.profilePicAlt = profilePicAlt;
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
