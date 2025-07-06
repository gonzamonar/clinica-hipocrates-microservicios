package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("PACIENTE")
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Patient extends User {

    @Column(name = "health_insurance")
    @EqualsAndHashCode.Include
    private String healthInsurance;

    @Column(name = "profile_pic_alt")
    @EqualsAndHashCode.Include
    private String profilePicAlt;


    @Override
    public void setSpecificFields(User user) {
        this.healthInsurance = ((Patient) user).healthInsurance;
        this.profilePicAlt = ((Patient) user).profilePicAlt;
    }


    //BUILDER
    private Patient(PatientBuilder builder) {
        super(builder);
        this.healthInsurance = builder.healthInsurance;
        this.profilePicAlt = builder.profilePicAlt;
    }

    public static PatientBuilder builder() {
        return new PatientBuilder();
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
}
