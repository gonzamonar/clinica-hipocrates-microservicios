package com.clinica_hipocrates.user_service.dto;

import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.model.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "_links", allowGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {
    @Schema(description = "Unique ID of the speciality", example = "1")
    protected Long id;

    @Schema(description = "Type of user", example = "PACIENTE")
    protected UserType userType;

    @Schema(description = "Name of the user", example = "Jane")
    protected String name;

    @Schema(description = "Lastname of the user", example = "Doe")
    protected String lastname;

    @Schema(description = "Age of the user", example = "32")
    protected Integer age;

    @Schema(description = "DNI of the user", example = "35.715.964")
    protected Integer dni;

    @Schema(description = "Email of the user", example = "jane.doe@gmail.com")
    protected String email;

    @Schema(description = "Path to profile pic", example = "jane-doe.png")
    protected String profilePic;

    @Schema(description = "Health insurance company of the Patient", example = "SwissMedical")
    private String healthInsurance;

    @Schema(description = "Alternative picture of the Patient", example = "jane-doe-alt.png")
    private String profilePicAlt;

    @Schema(description = "List of specialities IDs of the Specialist", example = "[{'id': 5, 'name': 'Cardiología'}, {'id': 2, 'name': 'Traumatología'}]")
    private List<SpecialityDTO> specialities;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }
}
