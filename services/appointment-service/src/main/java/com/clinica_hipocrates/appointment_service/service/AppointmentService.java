package com.clinica_hipocrates.appointment_service.service;

import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
    List<Appointment> findAll();
    Appointment findById(Long id);
    Appointment create(Appointment appointment);
    Appointment update(Long id, Appointment appointment);
    Appointment patchStatus(Long id, AppointmentStatus status);
    Appointment patchRating(Long id, Integer rating);
    void delete(Long id);
}
