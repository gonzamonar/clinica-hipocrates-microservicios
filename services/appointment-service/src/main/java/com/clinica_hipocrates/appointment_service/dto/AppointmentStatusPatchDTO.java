package com.clinica_hipocrates.appointment_service.dto;

import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentStatusPatchDTO {

    @Schema(description = "Status of the Appointment", example = "PENDIENTE")
    @NotNull
    private AppointmentStatus status;

}
