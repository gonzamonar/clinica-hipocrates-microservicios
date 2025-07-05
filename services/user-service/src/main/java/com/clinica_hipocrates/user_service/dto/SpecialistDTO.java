package com.clinica_hipocrates.user_service.dto;

import com.clinica_hipocrates.user_service.model.Speciality;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class SpecialistDTO extends AbstractUserDTO {
    @Schema(description = "List of specialities of the Specialist", example = "['Cardilogía', 'Kinesiología']")
    private List<Speciality> specialities;

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialistDTO that = (SpecialistDTO) o;
        return Objects.equals(specialities, that.specialities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialities);
    }
}
