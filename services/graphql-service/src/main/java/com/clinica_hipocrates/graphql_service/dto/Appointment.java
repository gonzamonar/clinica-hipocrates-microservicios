package com.clinica_hipocrates.graphql_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private Long id;
    private AppointmentStatus status;
    private Long specialistId;
    private Long specialityId;
    private Long patientId;
    private String dateTime;
    private Long commentId;
    private Long reviewId;
    private Long surveyId;
    private Integer rating;
}
