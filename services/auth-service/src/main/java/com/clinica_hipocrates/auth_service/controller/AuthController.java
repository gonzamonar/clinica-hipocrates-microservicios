package com.clinica_hipocrates.auth_service.controller;

import com.clinica_hipocrates.auth_service.assembler.AuthUserDTOAssembler;
import com.clinica_hipocrates.auth_service.dto.AuthUserDTO;
import com.clinica_hipocrates.auth_service.dto.LoginRequest;
import com.clinica_hipocrates.auth_service.dto.LoginResponse;
import com.clinica_hipocrates.auth_service.dto.RegistrationRequest;
import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.service.AuthService;
import com.clinica_hipocrates.auth_service.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AuthController {

    private final AuthService service;
    private final JwtService jwtService;
    private final AuthUserDTOAssembler assembler;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthService service,
            JwtService jwtService,
            AuthUserDTOAssembler assembler,
            AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.assembler = assembler;
        this.authenticationManager = authenticationManager;
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/auth/users")
    public ResponseEntity<List<AuthUserDTO>> getUsers() {
        return ResponseEntity.ok(assembler.toModelList(service.getAllUsers()));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/auth/users/{uuid}")
    public ResponseEntity<AuthUserDTO> getUserById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(assembler.toModel(service.getUserById(uuid)));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/auth/users/alias/{alias}")
    public ResponseEntity<AuthUserDTO> getUserByAlias(@PathVariable Long alias) {
        return ResponseEntity.ok(assembler.toModel(service.getUserByAlias(alias)));
    }


    @PostMapping("/auth/register")
    public ResponseEntity<AuthUserDTO> register(@Valid @RequestBody RegistrationRequest request) {
        AuthUser user = service.register(request.getEmail(), request.getPwd(), request.getUserType(), request.getProfileData());
        return ResponseEntity.ok(assembler.toModel(user));
    }


    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPwd())
        );
        AuthUser user = (AuthUser) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
