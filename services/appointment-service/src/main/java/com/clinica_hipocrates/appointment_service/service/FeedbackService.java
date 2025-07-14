package com.clinica_hipocrates.appointment_service.service;

import com.clinica_hipocrates.appointment_service.model.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> findAll();
    Feedback findById(Long id);
    Feedback create(Feedback feedback);
    Feedback update(Long id, Feedback feedback);
    void delete(Long id);
}
