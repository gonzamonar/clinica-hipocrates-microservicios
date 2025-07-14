package com.clinica_hipocrates.appointment_service.dto;

import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "_links", allowGetters = true)
public class AppointmentDTO extends RepresentationModel<AppointmentDTO> {

    @Schema(description = "Unique ID of the Appointment", example = "4")
    private Long id;

    @Schema(description = "Status of the Appointment", example = "PENDIENTE")
    @NotNull
    private AppointmentStatus status;

    @Schema(description = "Unique ID of the Specialist in the Appointment", example = "2")
    @NotNull @Positive
    private Long specialistId;

    @Schema(description = "Unique ID of the Specialist's Speciality", example = "Cardiología")
    @NotNull @Positive
    private Long specialityId;

    @Schema(description = "Unique ID of the Patient in the Appointment", example = "1")
    @NotNull @Positive
    private Long patientId;

    @Schema(description = "DateTime (Date and Hour) of the Appointment", example = "2025-07-07 15:30:00")
    private LocalDateTime dateTime;

    @Schema(description = "Unique ID of the Comment associated with the Appointment", example = "4")
    private Long commentId;

    @Schema(description = "Unique ID of the Review associated with the Appointment", example = "4")
    private Long reviewId;

    @Schema(description = "Unique ID of the Survey associated with the Appointment", example = "4")
    private Long surveyId;

    @Schema(description = "Rating (1 to 5) of Appointment given by the Patient", example = "3")
    private Integer rating;

    @Schema(description = "Unique ID of the Medical History associated with the Appointment", example = "")
    private Long medicalHistoryId;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }
}
