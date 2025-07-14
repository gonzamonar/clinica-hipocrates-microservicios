package com.clinica_hipocrates.appointment_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "_links", allowGetters = true)
public class ScheduleDTO extends RepresentationModel<ScheduleDTO> {

    @Schema(description = "Unique ID of the Schedule", example = "2")
    private Long id;

    @Schema(description = "Unique ID of the Specialist", example = "7")
    @NotNull @Positive
    private Long specialistId;

    @Schema(description = "Day of the week", example = "Lunes")
    @NotNull
    private DayOfWeek day;

    @Schema(description = "Starting time of the shift", example = "12:00")
    @NotNull
    private LocalTime start;

    @Schema(description = "Ending time of the shift", example = "17:00")
    @NotNull
    private LocalTime end;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }
}
