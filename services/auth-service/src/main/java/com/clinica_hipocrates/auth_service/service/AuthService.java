package com.clinica_hipocrates.auth_service.service;

import com.clinica_hipocrates.auth_service.dto.ValidationRequest;
import com.clinica_hipocrates.auth_service.event.UserRegisteredEvent;
import com.clinica_hipocrates.auth_service.messaging.publisher.UserRegisteredEventPublisher;
import com.clinica_hipocrates.auth_service.model.UserType;
import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.repository.AuthUserRepository;
import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisteredEventPublisher publisher;


    public AuthService(
            AuthUserRepository repository,
            PasswordEncoder passwordEncoder,
            UserRegisteredEventPublisher publisher) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
    }

    public AuthUser getUserById(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + uuid + " no existe."));
    }

    public AuthUser getUserByAlias(Long alias) {
        return repository.findByAlias(alias)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + alias + " no existe."));
    }

    public List<AuthUser> getAllUsers() {
        return repository.findAll();
    }

    public AuthUser register(String email, String pwd, UserType type, Map<String, Object> profileData) {
        if (repository.existsByEmail(email)) {
            throw new DuplicateResourceException("Ya existe una cuenta con ese email.");
        }

        ValidationRequest request = new ValidationRequest(email, type, profileData);
        validateProfileData(request);

        String hashedPwd = passwordEncoder.encode(pwd);
        AuthUser newUser = AuthUser.builder()
                .id(UUID.randomUUID())
                .alias(repository.getNextAlias())
                .email(email)
                .pwd(hashedPwd)
                .type(type)
                .enabled(!type.equals(UserType.SPECIALIST))
                .verified(false)
                .build();

        AuthUser createdUser = repository.save(newUser);

        // Publish event to fulfill profile creation
        UserRegisteredEvent event = UserRegisteredEvent.loadEvent(createdUser, profileData);
        publisher.publishUserRegisteredEvent(event);
        return createdUser;
    }

}
