package com.clinica_hipocrates.user_service.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.util.Objects;

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
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @Column(name = "user_type", insertable=false, updatable=false)
    @Enumerated(EnumType.STRING)
    protected UserType userType;
    @Column(name = "name")
    protected String name;
    @Column(name = "lastname")
    protected String lastname;
    @Column(name = "age")
    protected Integer age;
    @Column(name = "dni", unique = true)
    protected Integer dni;
    @Column(name = "email", unique = true)
    protected String email;
    @Column(name = "profile_pic")
    protected String profilePic;

    protected User() { }

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

    public abstract void setSpecificFields(User user);

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", dni=" + dni +
                ", email='" + email + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", userType=" + userType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(lastname, user.lastname) && Objects.equals(age, user.age) && Objects.equals(dni, user.dni) && Objects.equals(email, user.email) && Objects.equals(profilePic, user.profilePic) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, age, dni, email, profilePic, userType);
    }
}
