package com.clinica_hipocrates.appointment_service.assembler;

import com.clinica_hipocrates.appointment_service.controller.FeedbackController;
import com.clinica_hipocrates.appointment_service.dto.FeedbackDTO;
import com.clinica_hipocrates.appointment_service.model.Feedback;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FeedbackDTOAssembler
        extends RepresentationModelAssemblerSupport<Feedback, FeedbackDTO>
        implements DTOAssembler<Feedback, FeedbackDTO> {

    public FeedbackDTOAssembler() {
        super(FeedbackController.class, FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO toModel(Feedback feedback) {
        FeedbackDTO dto = instantiateModel(feedback);
        dto.setId(feedback.getId());
        dto.setType(feedback.getType());
        dto.setAppointmentId(feedback.getAppointmentId());
        dto.setAuthorId(feedback.getAuthorId());
        dto.setReason(feedback.getReason());
        dto.setText(feedback.getText());

        // Self link
        dto.add(linkTo(methodOn(FeedbackController.class)
                .getById(feedback.getId()))
                .withSelfRel());

        return dto;
    }

    public Feedback toEntity(FeedbackDTO dto) {
        Feedback feedback = new Feedback();
        feedback.setId(dto.getId());
        feedback.setType(dto.getType());
        feedback.setAppointmentId(dto.getAppointmentId());
        feedback.setAuthorId(dto.getAuthorId());
        feedback.setReason(dto.getReason());
        feedback.setText(dto.getText());
        return feedback;
    }
}
