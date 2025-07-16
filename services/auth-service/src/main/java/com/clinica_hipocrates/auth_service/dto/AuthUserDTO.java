package com.clinica_hipocrates.auth_service.dto;

import com.clinica_hipocrates.auth_service.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDTO extends RepresentationModel<AuthUserDTO> {

    @Schema(description = "User UUID", example = "779f64cd-dd60-4d53-b4b3-bc331b91e647")
    private UUID id;

    @Schema(description = "User ID alias (a number)", example = "3")
    private Long alias;

    @Schema(description = "User email", example = "admin@mail.com")
    private String email;

    @Schema(description = "User type (ADMIN, PATIENT, SPECIALIST)", example = "ADMIN")
    private UserType type;

    @Schema(description = "User's enabled status (true/false)", example = "true")
    private Boolean enabled;

    @Schema(description = "User's email verification status (true/false)", example = "false")
    private Boolean verified;
}
