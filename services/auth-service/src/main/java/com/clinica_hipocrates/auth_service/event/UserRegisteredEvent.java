package com.clinica_hipocrates.auth_service.event;

import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.model.UserType;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class UserRegisteredEvent {
    private UUID id;
    private Long alias;
    private String email;
    private UserType type;
    private Boolean enabled;
    private Map<String, Object> profileData;


    public static UserRegisteredEvent loadEvent(AuthUser newUser, Map<String, Object> profileData) {
        UserRegisteredEvent event = new UserRegisteredEvent();
        event.setId(newUser.getId());
        event.setAlias(newUser.getAlias());
        event.setEmail(newUser.getEmail());
        event.setType(newUser.getType());
        event.setEnabled(newUser.getEnabled());
        event.setProfileData(profileData);
        return event;
    }
}
