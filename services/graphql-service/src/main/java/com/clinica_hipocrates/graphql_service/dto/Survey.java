package com.clinica_hipocrates.graphql_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    private Long id;
    private Long appointmentId;
    private Integer websiteCalification;
    private String websiteComment;
    private Integer specialistCalification;
    private String specialistComment;
    private Integer appointmentCalification;
    private String appointmentComment;
}
