package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.FeedbackDTOAssembler;
import com.clinica_hipocrates.appointment_service.dto.FeedbackDTO;
import com.clinica_hipocrates.appointment_service.model.Feedback;
import com.clinica_hipocrates.appointment_service.service.FeedbackService;
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
@RequestMapping("/feedback")
@Tag(name = "/feedback", description = "Manage feedback (comments and reviews) of the appointments.")
public class FeedbackController {

    private final FeedbackService service;
    private final FeedbackDTOAssembler assembler;

    public FeedbackController(FeedbackService service,
                              FeedbackDTOAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all appointments")
    @ApiResponse(responseCode = "200", description = "OK: List of all appointments")
    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAll() {
        return ResponseEntity.ok(assembler.toModelList(service.findAll()));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Appointment found."),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    })
    public ResponseEntity<FeedbackDTO> getById(@PathVariable Long id) {
        Feedback feedback = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(feedback));
    }


    @PostMapping
    @Operation(summary = "Create a new appointment")
    @ApiResponse(responseCode = "201", description = "CREATED: Appointment created.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST: Non existing Specialist/Speciality/Patient ID sent.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: Appointment exists (same patient / doctor & datetime).")
    public ResponseEntity<FeedbackDTO> create(@Valid @RequestBody FeedbackDTO feedbackDTO) {
        Feedback savedFeedback = service.create(assembler.toEntity(feedbackDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedFeedback));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update the status of an appointment by ID. (Doesn't create a new one if ID doesn't exist.)")
    @ApiResponse(responseCode = "200", description = "OK: Status of the Appointment updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid appointmentStatus.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST: Invalid Appointment status transition.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Appointment doesn't exist.")
    public ResponseEntity<FeedbackDTO> update(@PathVariable Long id, @Valid @RequestBody FeedbackDTO dto) {
        Feedback updatedAppointment = service.update(id, assembler.toEntity(dto));
        return ResponseEntity.ok(assembler.toModel(updatedAppointment));
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
