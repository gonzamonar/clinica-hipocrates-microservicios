package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.user_service.dto.SpecialistDTO;
import com.clinica_hipocrates.user_service.model.Specialist;
import org.springframework.stereotype.Component;

@Component
public class SpecialistDTOAssembler extends AbstractUserDTOAssembler<Specialist, SpecialistDTO> {

    @Override
    protected SpecialistDTO createDtoInstance() {
        return new SpecialistDTO();
    }

    @Override
    protected Specialist createEntityInstance() {
        return new Specialist();
    }

    @Override
    protected void mapSpecificFieldsToDTO(Specialist user, SpecialistDTO dto) {
        dto.setSpecialities(user.getSpecialities());
    }

    @Override
    protected void mapSpecificFieldsToEntity(SpecialistDTO dto, Specialist user) {
        user.setSpecialities(dto.getSpecialities());
    }
}
