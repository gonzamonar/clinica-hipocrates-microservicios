package com.clinica_hipocrates.appointment_service.assembler;

import com.clinica_hipocrates.appointment_service.controller.AppointmentController;
import com.clinica_hipocrates.appointment_service.controller.SurveyController;
import com.clinica_hipocrates.appointment_service.dto.SurveyDTO;
import com.clinica_hipocrates.appointment_service.model.Survey;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SurveyDTOAssembler
        extends RepresentationModelAssemblerSupport<Survey, SurveyDTO>
        implements DTOAssembler<Survey, SurveyDTO> {

    public SurveyDTOAssembler() {
        super(SurveyController.class, SurveyDTO.class);
    }

    @Override
    public SurveyDTO toModel(Survey survey) {
        SurveyDTO dto = instantiateModel(survey);
        dto.setId(survey.getId());
        dto.setAppointmentId(survey.getAppointmentId());
        dto.setWebsiteCalification(survey.getWebsiteCalification());
        dto.setWebsiteComment(survey.getWebsiteComment());
        dto.setSpecialistCalification(survey.getSpecialistCalification());
        dto.setSpecialistComment(survey.getSpecialistComment());
        dto.setAppointmentCalification(survey.getAppointmentCalification());
        dto.setAppointmentComment(survey.getAppointmentComment());

        // Self link
        dto.add(linkTo(methodOn(AppointmentController.class)
                .getById(survey.getId()))
                .withSelfRel());

        return dto;
    }

    public Survey toEntity(SurveyDTO dto) {
        Survey survey = new Survey();
        survey.setId(dto.getId());
        survey.setAppointmentId(dto.getAppointmentId());
        survey.setWebsiteCalification(dto.getWebsiteCalification());
        survey.setWebsiteComment(dto.getWebsiteComment());
        survey.setSpecialistCalification(dto.getSpecialistCalification());
        survey.setSpecialistComment(dto.getSpecialistComment());
        survey.setAppointmentCalification(dto.getAppointmentCalification());
        survey.setAppointmentComment(dto.getAppointmentComment());

        return survey;
    }
}
