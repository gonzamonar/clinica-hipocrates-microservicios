package com.clinica_hipocrates.appointment_service.repository;

import com.clinica_hipocrates.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPatientIdAndDateTime(Long patientId, LocalDateTime dateTime);
    boolean existsBySpecialistIdAndDateTime(Long specialistId, LocalDateTime dateTime);
}
