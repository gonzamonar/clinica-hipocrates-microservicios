package com.clinica_hipocrates.appointment_service.repository;

import com.clinica_hipocrates.appointment_service.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
