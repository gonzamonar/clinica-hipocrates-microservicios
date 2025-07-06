package com.clinica_hipocrates.user_service.dto;

import com.clinica_hipocrates.user_service.model.Speciality;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistDTO extends AbstractUserDTO {
    @Schema(description = "List of specialities of the Specialist", example = "['Cardilogía', 'Kinesiología']")
    private List<Speciality> specialities;
}
