package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() { }

    private Admin(AdminBuilder builder) {
        super(builder);
    }

    public static AdminBuilder builder() {
        return new AdminBuilder();
    }

    @Override
    public void setSpecificFields(User user) {}

    public static class AdminBuilder extends UserBuilder<Admin, AdminBuilder> {
        @Override
        protected AdminBuilder self() {
            return this;
        }

        @Override
        public Admin build() {
            return new Admin(this);
        }
    }

    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
