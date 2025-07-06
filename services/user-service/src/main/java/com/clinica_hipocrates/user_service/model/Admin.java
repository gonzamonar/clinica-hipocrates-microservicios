package com.clinica_hipocrates.user_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue("ADMIN")
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    private Admin(AdminBuilder builder) {
        super(builder);
    }

    public static AdminBuilder builder() {
        return new AdminBuilder();
    }

    @Override
    public void setSpecificFields(User user) {
        // No specific fields related to class, fulfilling implementation
    }

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
}
