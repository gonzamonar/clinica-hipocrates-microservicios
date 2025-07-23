package com.clinica_hipocrates.user_service.event;

import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private Long id;
    private Boolean enabled;
    private String userType;
    private String name;
    private String lastname;
    private Set<Long> specialityIds;


    public static UserCreatedEvent loadEvent(User newUser) {
        UserCreatedEvent event = new UserCreatedEvent();
        event.setId(newUser.getId());
        event.setEnabled(newUser.getEnabled());
        event.setUserType(newUser.getUserType().toString());
        event.setName(newUser.getName());
        event.setLastname(newUser.getLastname());

        if (newUser.getUserType().equals(UserType.ESPECIALISTA)) {
            event.setSpecialityIds(getSpecialitiesIdAsSet(newUser));
        } else {
            event.setSpecialityIds(null);
        }

        return event;
    }

    private static Set<Long> getSpecialitiesIdAsSet(User specialist) {
        return specialist.getSpecialities().stream()
                .map(Speciality::getId)
                .collect(Collectors.toSet());
    }
}
