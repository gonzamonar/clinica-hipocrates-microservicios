package com.clinica_hipocrates.user_service.dto;

import com.clinica_hipocrates.common.util.TrimStringDeserializer;
import com.clinica_hipocrates.user_service.model.UserType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @Schema(description = "Unique ID of the speciality", example = "1")
    protected Long id;

    @Schema(description = "Type of user", example = "PACIENTE")
    @NotNull
    protected UserType userType;

    @Schema(description = "If user is enabled", example = "true")
    protected Boolean enabled;

    @Schema(description = "Name of the user", example = "Jane")
    @NotBlank
    @NotNull
    protected String name;

    @Schema(description = "Lastname of the user", example = "Doe")
    @NotBlank @NotNull
    protected String lastname;

    @Schema(description = "Age of the user", example = "32")
    @NotNull @Min(1) @Max(120)
    protected Integer age;

    @Schema(description = "DNI of the user", example = "35.715.964")
    @NotNull @Min(1) @Max(999999999)
    protected Integer dni;

    @Schema(description = "Email of the user", example = "jane.doe@gmail.com")
    @JsonDeserialize(using = TrimStringDeserializer.class)
    @NotBlank @NotNull @Email
    protected String email;

    @Schema(description = "Path to profile pic", example = "jane-doe.png")
    protected String profilePic;

    @Schema(description = "Health insurance company of the Patient", example = "SwissMedical")
    private String healthInsurance;

    @Schema(description = "Alternative picture of the Patient", example = "jane-doe-alt.png")
    private String profilePicAlt;

    @Schema(description = "List of specialities IDs of the Specialist", example = "[5, 2, 3]")
    private List<Long> specialities;
}
