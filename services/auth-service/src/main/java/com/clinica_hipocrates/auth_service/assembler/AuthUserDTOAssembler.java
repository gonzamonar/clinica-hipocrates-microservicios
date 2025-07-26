package com.clinica_hipocrates.auth_service.assembler;

import com.clinica_hipocrates.auth_service.controller.AuthController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import com.clinica_hipocrates.auth_service.dto.AuthUserDTO;
import com.clinica_hipocrates.auth_service.model.AuthUser;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthUserDTOAssembler
        extends RepresentationModelAssemblerSupport<AuthUser, AuthUserDTO>
        implements DTOAssembler<AuthUser, AuthUserDTO> {

        public AuthUserDTOAssembler() {
        super(AuthController.class, AuthUserDTO.class);
    }

    @Override
    public AuthUserDTO toModel(AuthUser user) {
        AuthUserDTO dto = instantiateModel(user);

        dto.setId(user.getId());
        //dto.setAlias(user.getAlias());
        dto.setEmail(user.getEmail());
        dto.setType(user.getType());
        dto.setEnabled(user.getEnabled());
        dto.setVerified(user.getVerified());

        // Self link
        dto.add(linkTo(methodOn(AuthController.class)
                .getUserById(user.getId()))
                .withSelfRel());

        return dto;
    }

    public AuthUser toEntity(AuthUserDTO dto) {
        AuthUser user = new AuthUser();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setType(dto.getType());
        user.setEnabled(dto.getEnabled());
        user.setVerified(dto.getVerified());
        return user;
    }
}
