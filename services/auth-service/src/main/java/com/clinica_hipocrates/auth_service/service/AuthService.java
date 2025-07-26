package com.clinica_hipocrates.auth_service.service;

import com.clinica_hipocrates.auth_service.dto.RegistrationRequest;
import com.clinica_hipocrates.auth_service.event.UserRegisteredEvent;
import com.clinica_hipocrates.auth_service.messaging.publisher.UserRegisteredEventPublisher;
import com.clinica_hipocrates.auth_service.model.UserType;
import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.repository.AuthUserRepository;
import com.clinica_hipocrates.common.exception.ApiError;
import com.clinica_hipocrates.common.exception.BadRequestException;
import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisteredEventPublisher publisher;
    private final RestTemplate restTemplate;


    public AuthService(
            AuthUserRepository repository,
            PasswordEncoder passwordEncoder,
            RestTemplate restTemplate,
            UserRegisteredEventPublisher publisher) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.publisher = publisher;
    }

    public AuthUser getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con id " + id + " no existe."));
    }

    public List<AuthUser> getAllUsers() {
        return repository.findAll();
    }

    public AuthUser register(RegistrationRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Ya existe una cuenta con ese email.");
        }

        try {
            restTemplate.postForEntity("http://localhost:8082/users/validate", request, String.class);
        } catch (RestClientResponseException ex) {
            ObjectMapper mapper = new ObjectMapper();
            String rawBody = ex.getResponseBodyAsString();

            try {
                JsonNode root = mapper.readTree(rawBody);
                String message = root.path("message").asText();
                throw new BadRequestException(message);
            } catch (IOException jsonParseEx) {
                // Fallback in case the body isn't valid JSON
                throw new BadRequestException(rawBody);
            }
        }

        String hashedPwd = passwordEncoder.encode(request.getPwd());
        AuthUser newUser = AuthUser.builder()
                .email(request.getEmail())
                .pwd(hashedPwd)
                .type(request.getUserType())
                .enabled(!request.getUserType().equals(UserType.SPECIALIST))
                .verified(false)
                .build();

        AuthUser createdUser = repository.save(newUser);

        // Publish event to fulfill profile creation
        UserRegisteredEvent event = UserRegisteredEvent.loadEvent(createdUser, profileData);
        publisher.publishUserRegisteredEvent(event);
        return createdUser;
    }

}
