package com.clinica_hipocrates.appointment_service.service;

import com.clinica_hipocrates.appointment_service.model.Survey;

import java.util.List;

public interface SurveyService {
    List<Survey> findAll();
    Survey findById(Long id);
    Survey create(Survey survey);
    Survey update(Long id, Survey survey);
    void delete(Long id);
}
