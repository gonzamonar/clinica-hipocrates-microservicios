package com.clinica_hipocrates.appointment_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "_links", allowGetters = true)
public class SurveyDTO extends RepresentationModel<SurveyDTO> {

    @Schema(description = "Unique ID of the Survey", example = "2")
    Long id;

    @Schema(description = "Unique ID of the Appointment related to the Survey", example = "1")
    @NotNull @Positive
    Long appointmentId;

    @Schema(description = "Calification (1 to 5) of the website", example = "5")
    @NotNull @Min(1) @Max(5)
    Integer websiteCalification;

    @Schema(description = "Comment/review about the website", example = "La página es muy clara y fácil de manejar.")
    @NotNull
    String websiteComment;

    @Schema(description = "Calification (1 to 5) of the Specialist", example = "4")
    @NotNull @Min(1) @Max(5)
    Integer specialistCalification;

    @Schema(description = "Comment/review about the Specialist", example = "Muy buena atención y trato profesional.")
    @NotNull
    String specialistComment;

    @Schema(description = "Calification (1 to 5) of the Appointment", example = "5")
    @NotNull @Min(1) @Max(5)
    Integer appointmentCalification;

    @Schema(description = "Comment/review about the Appointment", example = "Puntualidad, buena atención y facilidad.")
    @NotNull
    String appointmentComment;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }
}
