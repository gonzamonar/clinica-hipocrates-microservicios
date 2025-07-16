package com.clinica_hipocrates.auth_service.dto;

import com.clinica_hipocrates.auth_service.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @Schema(description = "Email of the user", example = "user@mail.com")
    @NotBlank @NotNull @Email
    String email;

    @NotNull @Length(min=6)
    String pwd;

    @NotNull
    UserType userType;

    @NotNull
    private Map<String, Object> profileData;
}
