package com.clinica_hipocrates.auth_service.event;

import com.clinica_hipocrates.auth_service.dto.RegistrationRequest;
import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.model.UserType;
import lombok.Data;

import java.util.Set;

@Data
public class UserRegisteredEvent {
    private Long id;
    private Boolean enabled;
    private UserType userType;
    private String name;
    private String lastname;
    private Integer age;
    private Integer dni;
    private String email;
    private String profilePic;
    private String healthInsurance;
    private String profilePicAlt;
    private Set<Long> specialityIds;


    public static UserRegisteredEvent loadEvent(AuthUser newUser, RegistrationRequest request) {
        UserRegisteredEvent event = new UserRegisteredEvent();
        //AuthUser fields
        event.setId(newUser.getId());
        event.setEnabled(newUser.getEnabled());
        event.setUserType(newUser.getType());
        event.setEmail(newUser.getEmail());

        //User Profile fields
        event.setName(request.getName());
        event.setLastname(request.getLastname());
        event.setAge(request.getAge());
        event.setDni(request.getDni());
        event.setProfilePic(request.getProfilePic());
        event.setHealthInsurance(request.getHealthInsurance());
        event.setProfilePicAlt(request.getProfilePicAlt());
        event.setSpecialityIds(request.getSpecialities());
        return event;
    }
}
