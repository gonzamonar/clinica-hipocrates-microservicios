package com.clinica_hipocrates.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO extends AbstractUserDTO {
    @Schema(description = "Health insurance company of the patient", example = "SwissMedical")
    @NotNull @NotBlank
    private String healthInsurance;

    @Schema(description = "Alternative picture of the patient", example = "/jane-doe-alt.png")
    private String profilePicAlt;
}
