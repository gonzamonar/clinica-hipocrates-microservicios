package com.clinica_hipocrates.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class PatientDTO extends AbstractUserDTO {
    @Schema(description = "Health insurance company of the patient", example = "SwissMedical")
    @NotNull @NotBlank
    private String healthInsurance;

    @Schema(description = "Alternative picture of the patient", example = "/jane-doe-alt.png")
    private String profilePicAlt;

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PatientDTO that = (PatientDTO) o;
        return Objects.equals(healthInsurance, that.healthInsurance) && Objects.equals(profilePicAlt, that.profilePicAlt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), healthInsurance, profilePicAlt);
    }
}
