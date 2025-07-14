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
    protected String userType;
    protected String name;
    protected String lastname;
    private Set<Long> specialityIds;
}
