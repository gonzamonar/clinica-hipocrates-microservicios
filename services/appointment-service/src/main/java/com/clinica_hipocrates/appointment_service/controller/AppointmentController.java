package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.AppointmentDTOAssembler;
import com.clinica_hipocrates.appointment_service.assembler.AppointmentRequestDTOAssembler;
import com.clinica_hipocrates.appointment_service.dto.AppointmentDTO;
import com.clinica_hipocrates.appointment_service.dto.AppointmentRatingPatchDTO;
import com.clinica_hipocrates.appointment_service.dto.AppointmentPostDTO;
import com.clinica_hipocrates.appointment_service.dto.AppointmentStatusPatchDTO;
import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.service.AppointmentService;
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
@RequestMapping("/appointments")
@Tag(name = "/appointments", description = "Manage medical appointments.")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentDTOAssembler assembler;
    private final AppointmentRequestDTOAssembler reqAssembler;

    public AppointmentController(AppointmentService service,
                                 AppointmentDTOAssembler assembler,
                                 AppointmentRequestDTOAssembler reqAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.reqAssembler = reqAssembler;
    }

    @Operation(summary = "Get all appointments")
    @ApiResponse(responseCode = "200", description = "OK: List of all appointments")
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll() {
        return ResponseEntity.ok(assembler.toModelList(service.findAll()));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Appointment found."),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    })
    public ResponseEntity<AppointmentDTO> getById(@PathVariable Long id) {
        Appointment appointment = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(appointment));
    }


    @PostMapping
    @Operation(summary = "Create a new appointment")
    @ApiResponse(responseCode = "201", description = "CREATED: Appointment created.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST: Non existing Specialist/Speciality/Patient ID sent.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: Appointment exists (same patient / doctor & datetime).")
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody AppointmentPostDTO appointmentRequest) {
        Appointment savedAppointment = service.create(reqAssembler.toEntity(appointmentRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedAppointment));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update an appointment by ID. (Doesn't create a new one if ID doesn't exist.)")
    @ApiResponse(responseCode = "200", description = "OK: Status of the Appointment updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid appointmentStatus.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST: Invalid Appointment status transition.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @Valid @RequestBody AppointmentDTO dto) {
        Appointment updatedAppointment = service.update(id, assembler.toEntity(dto));
        return ResponseEntity.ok(assembler.toModel(updatedAppointment));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update the status of an appointment by ID.")
    @ApiResponse(responseCode = "200", description = "OK: Status of the Appointment updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid appointmentStatus.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST: Invalid Appointment status transition.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    public ResponseEntity<AppointmentDTO> patchStatus(@PathVariable Long id, @Valid @RequestBody AppointmentStatusPatchDTO dto) {
        Appointment patchedAppointment = service.patchStatus(id, dto.getStatus());
        return ResponseEntity.ok(assembler.toModel(patchedAppointment));
    }

    @PatchMapping("/{id}/rating")
    @Operation(summary = "Update the rating of an appointment by ID.")
    @ApiResponse(responseCode = "200", description = "OK: Rating of the Appointment updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Missing or invalid rating.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    public ResponseEntity<AppointmentDTO> patchRating(@PathVariable Long id, @Valid @RequestBody AppointmentRatingPatchDTO dto) {
        Appointment patchedAppointment = service.patchRating(id, dto.getRating());
        return ResponseEntity.ok(assembler.toModel(patchedAppointment));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an appointment by ID")
    @ApiResponse(responseCode = "204", description = "NO_CONTENT: Appointment deleted.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    @ApiResponse(responseCode = "409", description = "CONFLICT: SQL constraint violation.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
