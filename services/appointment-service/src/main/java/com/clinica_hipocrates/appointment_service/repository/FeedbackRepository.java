package com.clinica_hipocrates.appointment_service.repository;

import com.clinica_hipocrates.appointment_service.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
