package com.clinica_hipocrates.appointment_service.dto;

import com.clinica_hipocrates.appointment_service.model.enums.FeedbackReason;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "_links", allowGetters = true)
public class FeedbackDTO extends RepresentationModel<FeedbackDTO> {

    @Schema(description = "Unique ID of the Feedback", example = "4")
    Long id;

    @Schema(description = "Feedback type", example = "REVIEW / COMMENT")
    FeedbackType type;

    @Schema(description = "ID of the Appointment associated to the Feedback", example = "2")
    Long appointmentId;

    @Schema(description = "ID of the User associated to the Feedback", example = "3")
    Long authorId;

    @Schema(description = "Feedback's reason", example = "Motivo de cancelación")
    FeedbackReason reason;

    @Schema(description = "Feedback's text", example = "El paciente no puede asistir.")
    String text;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }
}
