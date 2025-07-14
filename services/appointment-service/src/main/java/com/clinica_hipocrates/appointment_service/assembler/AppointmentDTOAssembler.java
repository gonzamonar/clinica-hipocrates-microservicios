package com.clinica_hipocrates.appointment_service.assembler;

import com.clinica_hipocrates.appointment_service.controller.AppointmentController;
import com.clinica_hipocrates.appointment_service.dto.AppointmentDTO;
import com.clinica_hipocrates.appointment_service.model.Appointment;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppointmentDTOAssembler
        extends RepresentationModelAssemblerSupport<Appointment, AppointmentDTO>
        implements DTOAssembler<Appointment, AppointmentDTO> {

    public AppointmentDTOAssembler() {
        super(AppointmentController.class, AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO toModel(Appointment appointment) {
        AppointmentDTO dto = instantiateModel(appointment);
        dto.setId(appointment.getId());
        dto.setStatus(appointment.getStatus());
        dto.setSpecialistId(appointment.getSpecialistId());
        dto.setSpecialityId(appointment.getSpecialityId());
        dto.setPatientId(appointment.getPatientId());
        dto.setDateTime(appointment.getDateTime());
        dto.setCommentId(appointment.getCommentId());
        dto.setReviewId(appointment.getReviewId());
        dto.setSurveyId(appointment.getSurveyId());
        dto.setRating(appointment.getRating());
        dto.setMedicalHistoryId(appointment.getMedicalHistoryId());

        // Self link
        dto.add(linkTo(methodOn(AppointmentController.class)
                .getById(appointment.getId()))
                .withSelfRel());

        return dto;
    }

    public Appointment toEntity(AppointmentDTO dto) {
        return Appointment.builder()
                        .id(dto.getId())
                        .status(dto.getStatus())
                        .specialistId(dto.getSpecialistId())
                        .specialityId(dto.getSpecialityId())
                        .patientId(dto.getPatientId())
                        .dateTime(dto.getDateTime())
                        .commentId(dto.getCommentId())
                        .reviewId(dto.getReviewId())
                        .surveyId(dto.getSurveyId())
                        .rating(dto.getRating())
                        .medicalHistoryId(dto.getMedicalHistoryId())
                        .build();
    }
}
