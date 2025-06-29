package com.clinica_hipocrates.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    @Column(name = "user_type", insertable=false, updatable=false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String name;
    private String lastname;
    private Integer age;
    @Column(unique = true)
    private Integer dni;
    @Column(unique = true)
    private String email;
    private String profilePic;

    public User() {
    }

    public User(Long id, UserType userType, String name, String lastname, Integer age, Integer dni, String email, String profilePic) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.dni = dni;
        this.email = email;
        this.profilePic = profilePic;
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
