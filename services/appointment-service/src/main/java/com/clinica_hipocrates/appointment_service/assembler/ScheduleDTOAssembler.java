package com.clinica_hipocrates.appointment_service.assembler;

import com.clinica_hipocrates.appointment_service.controller.ScheduleController;
import com.clinica_hipocrates.appointment_service.dto.ScheduleDTO;
import com.clinica_hipocrates.appointment_service.model.Schedule;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ScheduleDTOAssembler
        extends RepresentationModelAssemblerSupport<Schedule, ScheduleDTO>
        implements DTOAssembler<Schedule, ScheduleDTO> {

    public ScheduleDTOAssembler() {
        super(ScheduleController.class, ScheduleDTO.class);
    }

    @Override
    public ScheduleDTO toModel(Schedule schedule) {
        ScheduleDTO dto = instantiateModel(schedule);
        dto.setId(schedule.getId());
        dto.setSpecialistId(schedule.getSpecialistId());
        dto.setDay(schedule.getDay());
        dto.setStart(schedule.getStart());
        dto.setEnd(schedule.getEnd());

        // Self link
        dto.add(linkTo(methodOn(ScheduleController.class)
                .getById(schedule.getId()))
                .withSelfRel());

        return dto;
    }

    public Schedule toEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setSpecialistId(dto.getSpecialistId());
        schedule.setDay(dto.getDay());
        schedule.setStart(dto.getStart());
        schedule.setEnd(dto.getEnd());
        return schedule;
    }
}
