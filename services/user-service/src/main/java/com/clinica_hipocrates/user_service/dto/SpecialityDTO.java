package com.clinica_hipocrates.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
