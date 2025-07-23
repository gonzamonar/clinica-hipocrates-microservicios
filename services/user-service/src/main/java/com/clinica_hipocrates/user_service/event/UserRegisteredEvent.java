package com.clinica_hipocrates.user_service.event;

import com.clinica_hipocrates.user_service.model.UserType;
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
}
