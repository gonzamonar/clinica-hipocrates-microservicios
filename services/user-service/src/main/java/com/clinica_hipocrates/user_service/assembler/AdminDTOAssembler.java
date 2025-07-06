package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.user_service.dto.AdminDTO;
import com.clinica_hipocrates.user_service.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminDTOAssembler extends AbstractUserDTOAssembler<Admin, AdminDTO> {

    @Override
    protected AdminDTO createDtoInstance() { return new AdminDTO(); }

    @Override
    protected Admin createEntityInstance() { return new Admin(); }

    @Override
    protected void mapSpecificFieldsToDTO(Admin user, AdminDTO dto) {
        // No specific fields related to class, fulfilling implementation
    }

    @Override
    protected void mapSpecificFieldsToEntity(AdminDTO dto, Admin user) {
        // No specific fields related to class, fulfilling implementation
    }
}
