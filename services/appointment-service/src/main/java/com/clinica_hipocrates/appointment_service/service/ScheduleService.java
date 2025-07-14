package com.clinica_hipocrates.appointment_service.service;

import com.clinica_hipocrates.appointment_service.model.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();
    Schedule findById(Long id);
    Schedule create(Schedule schedule);
    Schedule update(Long id, Schedule schedule);
    void delete(Long id);
}
