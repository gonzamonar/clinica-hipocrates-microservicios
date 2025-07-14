package com.clinica_hipocrates.appointment_service.service.impl;

import com.clinica_hipocrates.appointment_service.model.Appointment;
import com.clinica_hipocrates.appointment_service.model.Feedback;
import com.clinica_hipocrates.appointment_service.model.Survey;
import com.clinica_hipocrates.appointment_service.model.enums.FeedbackType;
import com.clinica_hipocrates.appointment_service.repository.AppointmentRepository;
import com.clinica_hipocrates.appointment_service.repository.SurveyRepository;
import com.clinica_hipocrates.appointment_service.service.SurveyService;
import com.clinica_hipocrates.common.exception.BadRequestException;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository repository;
    private final AppointmentRepository appointmentRepository;

    public SurveyServiceImpl(
            SurveyRepository repository,
            AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Survey> findAll() {
        return repository.findAll();
    }

    @Override
    public Survey findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La encuesta con id " + id + " no existe."));
    }

    @Override
    public Survey create(Survey survey) {
        Appointment appointment = appointmentRepository.findById(survey.getAppointmentId())
                .orElseThrow(() -> new BadRequestException("El turno con id " + survey.getAppointmentId() + " no existe."));

        if (appointment.getSurveyId() != null) {
            throw new BadRequestException("El turno ya cuenta con una encuesta cargada.");
        }

        Survey createdSurvey = repository.save(survey);
        appointment.setSurveyId(createdSurvey.getId());
        appointmentRepository.save(appointment);
        return repository.save(survey);
    }

    @Override
    public Survey update(Long id, Survey survey) {
        Survey updatedSurvey = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La encuesta con id " + id + " no existe."));

        updatedSurvey.setAppointmentId(survey.getAppointmentId());
        updatedSurvey.setWebsiteCalification(survey.getWebsiteCalification());
        updatedSurvey.setWebsiteComment(survey.getWebsiteComment());
        updatedSurvey.setSpecialistCalification(survey.getSpecialistCalification());
        updatedSurvey.setSpecialistComment(survey.getSpecialistComment());
        updatedSurvey.setAppointmentCalification(survey.getAppointmentCalification());
        updatedSurvey.setAppointmentComment(survey.getAppointmentComment());
        repository.save(updatedSurvey);
        return updatedSurvey;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("La encuesta con id " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}
