package com.clinica_hipocrates.auth_service.controller;

import com.clinica_hipocrates.auth_service.assembler.AuthUserDTOAssembler;
import com.clinica_hipocrates.auth_service.dto.AuthUserDTO;
import com.clinica_hipocrates.auth_service.dto.LoginRequest;
import com.clinica_hipocrates.auth_service.dto.LoginResponse;
import com.clinica_hipocrates.auth_service.dto.RegistrationRequest;
import com.clinica_hipocrates.auth_service.model.AuthUser;
import com.clinica_hipocrates.auth_service.service.AuthService;
import com.clinica_hipocrates.auth_service.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/auth/users/{id}")
    public ResponseEntity<AuthUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.getUserById(id)));
    }


    @PostMapping("/auth/register")
    public ResponseEntity<AuthUser> register(@Valid @RequestBody RegistrationRequest request) {
        AuthUser user = service.register(request);
        //return ResponseEntity.ok(assembler.toModel(user));
        return ResponseEntity.ok(user);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPwd())
        );
        AuthUser user = (AuthUser) authentication.getPrincipal();

        String token = jwtService.generateToken(user);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok().body(new LoginResponse(false,"Login successful"));
    }


    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

}
