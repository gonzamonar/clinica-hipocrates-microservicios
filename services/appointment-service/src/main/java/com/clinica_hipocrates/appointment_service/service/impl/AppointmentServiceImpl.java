package com.clinica_hipocrates.appointment_service.service.impl;

import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.model.CachedUser;
import com.clinica_hipocrates.appointment_service.model.enums.AppointmentStatus;
import com.clinica_hipocrates.appointment_service.repository.AppointmentRepository;
import com.clinica_hipocrates.appointment_service.repository.CachedUserRepository;
import com.clinica_hipocrates.appointment_service.service.AppointmentService;
import com.clinica_hipocrates.common.exception.BadRequestException;
import com.clinica_hipocrates.common.exception.DuplicateResourceException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final CachedUserRepository cachedUsersRepository;

    public AppointmentServiceImpl(AppointmentRepository repository,
                                  CachedUserRepository cachedUsersRepository) {
        this.repository = repository;
        this.cachedUsersRepository = cachedUsersRepository;
    }

    @Override
    public List<Appointment> findAll() {
        return repository.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El turno con id " + id + " no existe."));
    }

    @Override
    public Appointment create(Appointment appointment) {
        validateAppointmentEntities(appointment);

        if (repository.existsByPatientIdAndDateTime(appointment.getPatientId(), appointment.getDateTime())) {
            throw new DuplicateResourceException("El paciente ya tiene un turno en ese día y horario.");
        }
        if (repository.existsBySpecialistIdAndDateTime(appointment.getSpecialistId(), appointment.getDateTime())) {
            throw new DuplicateResourceException("El especialista ya tiene un turno en ese día y horario.");
        }

        return repository.save(appointment);
    }

    @Override
    public Appointment update(Long id, Appointment appointment) {
        Appointment updatedAppointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El turno con id " + id + " no existe."));

        AppointmentStatus currentStatus = updatedAppointment.getStatus();
        AppointmentStatus newStatus = appointment.getStatus();
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new BadRequestException("Transición inválida del estado del turno de " + currentStatus + " a " + newStatus + ".");
        }

        validateAppointmentEntities(appointment);

        updatedAppointment.setStatus(newStatus);
        updatedAppointment.setSpecialistId(appointment.getSpecialistId());
        updatedAppointment.setSpecialityId(appointment.getSpecialityId());
        updatedAppointment.setPatientId(appointment.getPatientId());
        updatedAppointment.setDateTime(appointment.getDateTime());
        repository.save(updatedAppointment);
        return updatedAppointment;
    }

    @Override
    public Appointment patchStatus(Long id, AppointmentStatus newStatus) {
        Appointment updatedAppointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El turno con id " + id + " no existe."));

        AppointmentStatus currentStatus = updatedAppointment.getStatus();
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new BadRequestException("Transición inválida del estado del turno de " + currentStatus + " a " + newStatus + ".");
        }

        updatedAppointment.setStatus(newStatus);
        repository.save(updatedAppointment);
        return updatedAppointment;
    }

    @Override
    public Appointment patchRating(Long id, Integer rating) {
        Appointment updatedAppointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El turno con id " + id + " no existe."));
        updatedAppointment.setRating(rating);
        repository.save(updatedAppointment);
        return updatedAppointment;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("El turno con id " + id + " no existe.");
        }
        repository.deleteById(id);
    }

    public void validateAppointmentEntities(Appointment appointment) {
        CachedUser user = cachedUsersRepository.findById(appointment.getSpecialistId()).orElse(null);
        if (user == null || !user.getUserType().equals("ESPECIALISTA")) {
            throw new BadRequestException("El Especialista con id " + appointment.getSpecialistId() + " no existe.");
        } else if (!user.getSpecialityIds().contains(appointment.getSpecialityId())) {
            throw new BadRequestException("El Especialista no posee la especialidad con id " + appointment.getSpecialityId() + ".");
        }

        if (!cachedUsersRepository.existsByIdAndUserType(appointment.getPatientId(), "PACIENTE")) {
            throw new BadRequestException("El Paciente con id " + appointment.getPatientId() + " no existe.");
        }
    }
}
