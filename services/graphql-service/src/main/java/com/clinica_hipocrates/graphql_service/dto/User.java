package com.clinica_hipocrates.graphql_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private UserType userType;
    private String name;
    private String lastname;
    private Integer age;
    private Integer dni;
    private String email;
    private String profilePic;
    private String healthInsurance;
    private String profilePicAlt;
    private List<Speciality> specialities;
}
