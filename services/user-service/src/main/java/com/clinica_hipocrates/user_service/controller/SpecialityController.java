package com.clinica_hipocrates.user_service.controller;

import com.clinica_hipocrates.user_service.assembler.SpecialityDTOAssembler;
import com.clinica_hipocrates.user_service.dto.SpecialityDTO;
import com.clinica_hipocrates.user_service.model.Specialist;
import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.service.SpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialities")
@Tag(name = "/specialities", description = "Manage medical specialities.")
public class SpecialityController {

    private final SpecialityService service;
    private final SpecialityDTOAssembler assembler;

    public SpecialityController(SpecialityService service, SpecialityDTOAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all specialities")
    @ApiResponse(responseCode = "200", description = "OK: List of all specialities")
    @GetMapping
    public ResponseEntity<List<SpecialityDTO>> getAll() {
        return ResponseEntity.ok(assembler.toListModel(service.findAll()));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a speciality by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK: Speciality found."),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND: Speciality doesn't exist.")
    })
    public ResponseEntity<SpecialityDTO> getById(@PathVariable Long id) {
        Speciality speciality = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(speciality));
    }


    @GetMapping("/{id}/doctors")
    @Operation(summary = "Get list of doctors by speciality ID")
    @ApiResponse(responseCode = "200", description = "OK: List of all speciality's doctors")
    public ResponseEntity<List<Specialist>> getDoctorsBySpeciality(@PathVariable Long id) {
        return ResponseEntity.ok(service.findDoctorsBySpeciality(id));
    }


    @PostMapping
    @Operation(summary = "Create a new speciality")
    @ApiResponse(responseCode = "201", description = "CREATED: Speciality created.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank name.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: Speciality exists.")
    public ResponseEntity<SpecialityDTO> create(@Valid @RequestBody SpecialityDTO dtoSpeciality) {
        Speciality savedSpeciality = service.create(assembler.toEntity(dtoSpeciality));
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedSpeciality));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a speciality by ID. (Doesn't create a new one if ID doesn't exist.)")
    @ApiResponse(responseCode = "200", description = "OK: Speciality updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank name.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: Speciality exists.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Speciality doesn't exist.")
    public ResponseEntity<SpecialityDTO> update(@PathVariable Long id, @Valid @RequestBody SpecialityDTO dtoSpeciality) {
        Speciality updatedSpeciality = service.update(id, assembler.toEntity(dtoSpeciality));
        return ResponseEntity.ok(assembler.toModel(updatedSpeciality));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a speciality by ID")
    @ApiResponse(responseCode = "204", description = "NO_CONTENT: Speciality deleted.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Speciality doesn't exist.")
    @ApiResponse(responseCode = "409", description = "CONFLICT: SQL constraint violation.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
