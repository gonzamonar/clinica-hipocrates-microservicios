package com.clinica_hipocrates.user_service.controller;

import com.clinica_hipocrates.user_service.assembler.UserAssemblerRegistry;
import com.clinica_hipocrates.user_service.dto.AbstractUserDTO;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "/users", description = "Endpoints related to users (Admin, Specialist & Patients)")
public class UserController {

    private final UserService service;
    private final UserAssemblerRegistry assembler;

    public UserController(UserService service, UserAssemblerRegistry assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "OK: List of all users")
    public ResponseEntity<List<AbstractUserDTO>> getAll() {
        return ResponseEntity.ok(assembler.toDtoList(service.findAll()));
    @PostMapping("/bulk")
    @Operation(summary = "Get all users by set of ids")
    @ApiResponse(responseCode = "200", description = "OK: List of users")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    public ResponseEntity<List<UserResponseDTO>> getAllById(@Valid @RequestBody BulkRequestDTO bulk) {
        return ResponseEntity.ok(assembler.toModelList(service.findAllByIds(bulk.getIds())));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: User found."),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND: User doesn't exist.")
    })
    public ResponseEntity<AbstractUserDTO> getById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok(assembler.toDto(user));
    }

    @Deprecated
    @PostMapping
    @Operation(summary = "GONE - User creation is achieved through /auth/register")
    @ApiResponse(responseCode = "410", description = "GONE: Use /auth/register to create new users.")
    public void createUser() {
        throw new DeprecatedResourceException("DEPRECATED: Use /auth/register to create new users.");
    }

    @Profile("test")
    @RestController
    class TestController {
        @PostMapping("/users/test")
        @Operation(summary = "Create a new user")
        @ApiResponse(responseCode = "201", description = "CREATED: User created.")
        @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
        @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: User with Email or DNI exists.")
        public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userDTO) {
            User savedUser = service.create(assembler.toEntity(userDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedUser));
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update an user by ID. (Doesn't create a new one if ID doesn't exist.)")
    @ApiResponse(responseCode = "200", description = "OK: User updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: User with Email or DNI exists.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: User doesn't exist.")
    public ResponseEntity<AbstractUserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody AbstractUserDTO userDTO) {
        User updatedUser = service.update(id, assembler.toEntity(userDTO));
        return ResponseEntity.ok(assembler.toDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user by ID")
    @ApiResponse(responseCode = "204", description = "NO_CONTENT: User deleted.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: User doesn't exist.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate an user creation request.")
    @ApiResponse(responseCode = "204", description = "NO_CONTENT: Valid User.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: User with Email or DNI exists.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST: Specialities doesn't exist.")
    public ResponseEntity<Void> validate(@Valid @RequestBody UserRequestDTO userDTO) {
        if (userDTO.getUserType().equals(UserType.ESPECIALISTA)) {
            specialityService.validateSpecialities(userDTO.getSpecialities());
        }
        service.validate(assembler.toEntity(userDTO));
        return ResponseEntity.noContent().build();
    }
}
