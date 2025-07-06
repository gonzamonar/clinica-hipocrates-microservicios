package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.user_service.dto.AbstractUserDTO;
import com.clinica_hipocrates.user_service.model.User;

public interface UserAssembler<U extends User, D extends AbstractUserDTO> {
    D toModel(U entity);
    U toEntity(D dto);
}
