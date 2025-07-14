package com.clinica_hipocrates.appointment_service.controller;

import com.clinica_hipocrates.appointment_service.assembler.ScheduleDTOAssembler;
import com.clinica_hipocrates.appointment_service.dto.ScheduleDTO;
import com.clinica_hipocrates.appointment_service.model.Schedule;
import com.clinica_hipocrates.appointment_service.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@Tag(name = "/schedules", description = "Manage Specialists schedules for the appointments.")
public class ScheduleController {

    private final ScheduleService service;
    private final ScheduleDTOAssembler assembler;

    public ScheduleController(ScheduleService service,
                              ScheduleDTOAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }


    @Operation(summary = "Get all schedules")
    @ApiResponse(responseCode = "200", description = "OK: List of all the schedules")
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAll() {
        return ResponseEntity.ok(assembler.toModelList(service.findAll()));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a schedule by ID")
    @ApiResponse(responseCode = "200", description = "OK: Schedule found.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Schedule doesn't exist.")
    public ResponseEntity<ScheduleDTO> getById(@PathVariable Long id) {
        Schedule schedule = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(schedule));
    }


    @PostMapping
    @Operation(summary = "Create a new schedule")
    @ApiResponse(responseCode = "201", description = "CREATED: Schedule created.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    @ApiResponse(responseCode = "400", description = "DUPLICATE_RESOURCE: Schedule exists (same specialist & weekday).")
    public ResponseEntity<ScheduleDTO> create(@Valid @RequestBody ScheduleDTO dto) {
        Schedule savedSchedule = service.create(assembler.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedSchedule));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a schedule by ID. (Doesn't create a new one if ID doesn't exist.)")
    @ApiResponse(responseCode = "200", description = "OK: Status of the Appointment updated.")
    @ApiResponse(responseCode = "400", description = "VALIDATION_ERROR: Blank, missing or invalid fields.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Schedule doesn't exist.")
    public ResponseEntity<ScheduleDTO> update(@PathVariable Long id, @Valid @RequestBody ScheduleDTO dto) {
        Schedule updatedSchedule = service.update(id, assembler.toEntity(dto));
        return ResponseEntity.ok(assembler.toModel(updatedSchedule));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a schedule by ID")
    @ApiResponse(responseCode = "204", description = "NO_CONTENT: Schedule deleted.")
    @ApiResponse(responseCode = "404", description = "NOT_FOUND: Schedule doesn't exist.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
