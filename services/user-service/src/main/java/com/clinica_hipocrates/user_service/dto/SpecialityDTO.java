package com.clinica_hipocrates.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class SpecialityDTO extends RepresentationModel<SpecialityDTO> {
    @Schema(description = "Unique ID of the speciality", example = "2")
    private Long id;

    @Schema(description = "Name of the speciality", example = "Cardiology")
    @NotBlank @NotNull
    private String name;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }

    public SpecialityDTO() {}

    public SpecialityDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialityDTO that = (SpecialityDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name);
    }
}
