package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.common.util.InputFormatter;
import com.clinica_hipocrates.user_service.controller.SpecialityController;
import com.clinica_hipocrates.user_service.dto.SpecialityDTO;
import com.clinica_hipocrates.user_service.model.Speciality;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SpecialityDTOAssembler
        extends RepresentationModelAssemblerSupport<Speciality, SpecialityDTO>
        implements DTOAssembler<Speciality, SpecialityDTO> {

    public SpecialityDTOAssembler() {
        super(SpecialityController.class, SpecialityDTO.class);
    }

    @Override
    public SpecialityDTO toModel(Speciality speciality) {
        SpecialityDTO dto = instantiateModel(speciality);
        dto.setId(speciality.getId());
        dto.setName(InputFormatter.normalizeName(speciality.getName()));

        // Self link
        dto.add(linkTo(methodOn(SpecialityController.class)
                .getById(speciality.getId()))
                .withSelfRel());

        // Specialists link
        dto.add(linkTo(methodOn(SpecialityController.class)
                .getDoctorsBySpeciality(speciality.getId()))
                .withRel("specialists"));

        return dto;
    }

    public Speciality toEntity(SpecialityDTO dto) {
        Speciality s = new Speciality();
        s.setName(InputFormatter.normalizeName(dto.getName()));
        return s;
    }
}
