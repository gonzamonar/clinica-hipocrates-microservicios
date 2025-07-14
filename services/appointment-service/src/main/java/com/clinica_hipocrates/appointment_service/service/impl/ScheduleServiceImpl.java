package com.clinica_hipocrates.appointment_service.service.impl;

import com.clinica_hipocrates.appointment_service.model.Schedule;
import com.clinica_hipocrates.appointment_service.repository.ScheduleRepository;
import com.clinica_hipocrates.appointment_service.service.ScheduleService;
import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Schedule> findAll() {
        return repository.findAll();
    }

    @Override
    public Schedule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El horario con id " + id + " no existe."));
    }

    @Override
    public Schedule create(Schedule schedule) {
        return repository.save(schedule);
    }

    @Override
    public Schedule update(Long id, Schedule schedule) {
        Schedule updatedSchedule = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El horario con id " + id + " no existe."));

        updatedSchedule.setSpecialistId(schedule.getSpecialistId());
        updatedSchedule.setDay(schedule.getDay());
        updatedSchedule.setStart(schedule.getStart());
        updatedSchedule.setEnd(schedule.getEnd());
        repository.save(updatedSchedule);
        return updatedSchedule;
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("El horario con id " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}
