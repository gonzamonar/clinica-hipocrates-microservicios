package com.clinica_hipocrates.user_service.dto;

import com.clinica_hipocrates.user_service.model.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "userType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = SpecialistDTO.class, name = "ESPECIALISTA"),
        @JsonSubTypes.Type(value = PatientDTO.class, name = "PACIENTE")
})
@JsonIgnoreProperties(value = "_links", allowGetters = true)
public abstract class AbstractUserDTO extends RepresentationModel<AbstractUserDTO> {
    @Schema(description = "Unique ID of the speciality", example = "1")
    protected Long id;

    @Schema(description = "Type of user", example = "PATIENT")
    @NotBlank @NotNull
    protected UserType userType;

    @Schema(description = "Name of the user", example = "Jane")
    @NotBlank @NotNull
    protected String name;

    @Schema(description = "Lastname of the user", example = "Doe")
    @NotBlank @NotNull
    protected String lastname;

    @Schema(description = "Age of the user", example = "32")
    @NotBlank @NotNull @Min(1) @Max(120)
    protected Integer age;

    @Schema(description = "DNI of the user", example = "35.715.964")
    @NotBlank @NotNull @Min(1) @Max(999999999)
    protected Integer dni;

    @Schema(description = "Email of the user", example = "jane.doe@gmail.com")
    @NotBlank @NotNull @Email
    protected String email;

    @Schema(description = "Path to profile pic", example = "/jane-doe.png")
    protected String profilePic;

    @Override
    @Schema(hidden = true)
    public Links getLinks() {
        return super.getLinks();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractUserDTO abstractUserDTO = (AbstractUserDTO) o;
        return Objects.equals(id, abstractUserDTO.id) && userType == abstractUserDTO.userType && Objects.equals(name, abstractUserDTO.name) && Objects.equals(lastname, abstractUserDTO.lastname) && Objects.equals(age, abstractUserDTO.age) && Objects.equals(dni, abstractUserDTO.dni) && Objects.equals(email, abstractUserDTO.email) && Objects.equals(profilePic, abstractUserDTO.profilePic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userType, name, lastname, age, dni, email, profilePic);
    }
}
