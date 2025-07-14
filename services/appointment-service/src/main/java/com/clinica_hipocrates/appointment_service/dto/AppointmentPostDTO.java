package com.clinica_hipocrates.appointment_service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPostDTO {

    @NotNull @Positive
    private Long specialistId;

    @NotNull @Positive
    private Long specialityId;

    @NotNull @Positive
    private Long patientId;

    @NotNull @Future
    private LocalDateTime dateTime;
}
