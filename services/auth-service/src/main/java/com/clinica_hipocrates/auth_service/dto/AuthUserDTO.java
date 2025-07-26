package com.clinica_hipocrates.auth_service.dto;

import com.clinica_hipocrates.auth_service.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDTO extends RepresentationModel<AuthUserDTO> {

    @Schema(description = "User ID", example = "3")
    private Long id;

    @Schema(description = "User email", example = "admin@mail.com")
    private String email;

    @Schema(description = "User type (ADMIN, PATIENT, SPECIALIST)", example = "ADMIN")
    private UserType type;

    @Schema(description = "User's enabled status (true/false)", example = "true")
    private Boolean enabled;

    @Schema(description = "User's email verification status (true/false)", example = "false")
    private Boolean verified;
}
