package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
    }

    public Admin(Long id, UserType userType, String name, String lastname, Integer age, Integer dni, String email, String profilePic) {
        super(id, userType, name, lastname, age, dni, email, profilePic);
    }

    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
