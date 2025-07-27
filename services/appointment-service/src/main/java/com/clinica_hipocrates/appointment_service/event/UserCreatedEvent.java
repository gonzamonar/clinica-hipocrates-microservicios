package com.clinica_hipocrates.appointment_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private Long id;
    private String userType;
    private String name;
    private String lastname;
    private Boolean enabled;
    private Set<Long> specialityIds;
}
