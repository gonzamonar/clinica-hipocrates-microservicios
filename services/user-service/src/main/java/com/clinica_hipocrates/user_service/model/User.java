package com.clinica_hipocrates.user_service.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "userType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Patient.class, name = "PACIENTE"),
        @JsonSubTypes.Type(value = Specialist.class, name = "ESPECIALISTA"),
        @JsonSubTypes.Type(value = Admin.class, name = "ADMIN")
})
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "user_type", insertable=false, updatable=false)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    protected UserType userType;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    protected String name;

    @Column(name = "lastname")
    @EqualsAndHashCode.Include
    protected String lastname;

    @Column(name = "age")
    @EqualsAndHashCode.Include
    protected Integer age;

    @Column(name = "dni", unique = true)
    @EqualsAndHashCode.Include
    protected Integer dni;

    @Column(name = "email", unique = true)
    @EqualsAndHashCode.Include
    protected String email;

    @Column(name = "profile_pic")
    @EqualsAndHashCode.Include
    protected String profilePic;


    public abstract void setSpecificFields(User user);


    // BUILDER
    protected User(UserBuilder<?, ?> builder) {
        this.id = builder.id;
        this.userType = builder.userType;
        this.name = builder.name;
        this.lastname = builder.lastname;
        this.age = builder.age;
        this.dni = builder.dni;
        this.email = builder.email;
        this.profilePic = builder.profilePic;
    }

    public abstract static class UserBuilder<T extends User, B extends UserBuilder<T, B>> {
        private Long id;
        private UserType userType;
        private String name;
        private String lastname;
        private Integer age;
        private Integer dni;
        private String email;
        private String profilePic;

        public B id(Long id) {
            this.id = id;
            return self();
        }

        public B userType(UserType userType) {
            this.userType = userType;
            return self();
        }

        public B name(String name) {
            this.name = name;
            return self();
        }

        public B lastname(String lastname) {
            this.lastname = lastname;
            return self();
        }

        public B age(Integer age) {
            this.age = age;
            return self();
        }

        public B dni(Integer dni) {
            this.dni = dni;
            return self();
        }

        public B email(String email) {
            this.email = email;
            return self();
        }

        public B profilePic(String profilePic) {
            this.profilePic = profilePic;
            return self();
        }

        protected abstract B self();
        public abstract T build();
    }
}
