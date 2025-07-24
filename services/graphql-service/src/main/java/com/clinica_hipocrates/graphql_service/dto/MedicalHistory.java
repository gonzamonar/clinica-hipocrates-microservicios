package com.clinica_hipocrates.graphql_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistory {
    private Long id;
    private Appointment appointment;
    private User patient;
    private User specialist;
    private Integer height;
    private Float weight;
    private Float temperature;
    private Float pressure;
    private List<DynamicData> dynamicData;
}
