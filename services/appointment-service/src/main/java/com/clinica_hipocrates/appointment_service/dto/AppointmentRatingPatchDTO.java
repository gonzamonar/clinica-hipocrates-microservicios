package com.clinica_hipocrates.appointment_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRatingPatchDTO {

    @Schema(description = "Rating (1 to 5) of Appointment given by the Patient", example = "3")
    @NotNull @Min(1) @Max(5)
    private Integer rating;
}
