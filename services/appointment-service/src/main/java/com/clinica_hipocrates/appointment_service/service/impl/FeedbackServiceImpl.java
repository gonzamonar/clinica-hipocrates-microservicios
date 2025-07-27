package com.clinica_hipocrates.appointment_service.service.impl;

import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.model.CachedUser;
import com.clinica_hipocrates.appointment_service.model.Feedback;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
import com.clinica_hipocrates.appointment_service.repository.AppointmentRepository;
import com.clinica_hipocrates.appointment_service.repository.CachedUserRepository;
import com.clinica_hipocrates.appointment_service.repository.FeedbackRepository;
import com.clinica_hipocrates.appointment_service.service.FeedbackService;
import com.clinica_hipocrates.common.exception.BadRequestException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;
    private final AppointmentRepository appointmentRepository;
    private final CachedUserRepository cachedUserRepository;

    public FeedbackServiceImpl(
            FeedbackRepository repository,
            AppointmentRepository appointmentRepository,
            CachedUserRepository cachedUserRepository) {
        this.repository = repository;
        this.appointmentRepository = appointmentRepository;
        this.cachedUserRepository = cachedUserRepository;
    }

    @Override
    public List<Feedback> findAll() {
        return repository.findAll();
    }

    @Override
    public Feedback findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El comentario/reseña con id " + id + " no existe."));
    }

    @Override
    public Feedback create(Feedback feedback) {
        feedback.setId(null);
        verifyAuthorExistance(feedback);
        Appointment appointment = verifyAppointmentExistance(feedback);

        Feedback createdFeedback = repository.save(feedback);
        if (createdFeedback.getType().equals(FeedbackType.REVIEW)) {
            appointment.setReviewId(createdFeedback.getId());
        } else if (createdFeedback.getType().equals(FeedbackType.COMMENT)) {
            appointment.setCommentId(createdFeedback.getId());
        }
        appointmentRepository.save(appointment);
        return createdFeedback;
    }

    @Override
    public Feedback update(Long id, Feedback feedback) {
        Feedback updatedFeedback = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El comentario/reseña con id " + id + " no existe."));

        verifyAuthorExistance(feedback);

        if (!feedback.getAppointmentId().equals(updatedFeedback.getAppointmentId())) {
            Appointment updatedAppointment = verifyAppointmentExistance(feedback);
            updatedFeedback.setAppointmentId(feedback.getAppointmentId());
        }
        updatedFeedback.setType(feedback.getType());
        updatedFeedback.setAuthorId(feedback.getAuthorId());
        updatedFeedback.setReason(feedback.getReason());
        updatedFeedback.setText(feedback.getText());
        repository.save(updatedFeedback);
        return updatedFeedback;
    }

    private Appointment verifyAppointmentExistance(Feedback feedback) {
        Appointment appointment = appointmentRepository.findById(feedback.getAppointmentId())
                .orElseThrow(() -> new BadRequestException("El turno con id " + feedback.getAppointmentId() + " no existe."));

        if (feedback.getType().equals(FeedbackType.COMMENT) && appointment.getCommentId() != null) {
            throw new BadRequestException("El turno ya cuenta con un comentario cargado.");
        } else if ((feedback.getType().equals(FeedbackType.REVIEW)) && appointment.getReviewId() != null) {
            throw new BadRequestException("El turno ya cuenta con una reseña cargada.");
        }
        return appointment;
    }

    private void verifyAuthorExistance(Feedback feedback) {
        cachedUserRepository.findById(feedback.getAuthorId())
                .orElseThrow(() -> new BadRequestException("El autor con id " + feedback.getAuthorId() + " no existe."));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("El comentario/reseña con id " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}
