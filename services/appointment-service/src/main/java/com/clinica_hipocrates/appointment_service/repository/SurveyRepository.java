package com.clinica_hipocrates.appointment_service.repository;

import com.clinica_hipocrates.appointment_service.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
