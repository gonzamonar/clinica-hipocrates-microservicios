package com.clinica_hipocrates.user_service.dto;

import com.clinica_hipocrates.common.util.TrimStringDeserializer;
import com.clinica_hipocrates.user_service.model.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "userType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = SpecialistDTO.class, name = "ESPECIALISTA"),
        @JsonSubTypes.Type(value = PatientDTO.class, name = "PACIENTE")
})
@JsonIgnoreProperties(value = "_links", allowGetters = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractUserDTO extends RepresentationModel<AbstractUserDTO> {
    @Schema(description = "Unique ID of the speciality", example = "1")
    protected Long id;

    @Schema(description = "Type of user", example = "PACIENTE")
    @NotNull
    protected UserType userType;

    @Schema(description = "Name of the user", example = "Jane")
    @NotBlank @NotNull
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

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }
}
