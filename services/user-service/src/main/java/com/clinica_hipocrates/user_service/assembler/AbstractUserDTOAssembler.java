package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.common.util.InputFormatter;
import com.clinica_hipocrates.user_service.controller.UserController;
import com.clinica_hipocrates.user_service.dto.AbstractUserDTO;
import com.clinica_hipocrates.user_service.model.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class AbstractUserDTOAssembler<U extends User, D extends AbstractUserDTO>
        implements UserAssembler<U, D> {

    protected abstract D createDtoInstance();
    protected abstract U createEntityInstance();
    protected abstract void mapSpecificFieldsToDTO(U user, D dto);
    protected abstract void mapSpecificFieldsToEntity(D dto, U user);

    public D toModel(U user) {
        D dto = createDtoInstance();
        mapSharedFieldsToDTO(user, dto);
        mapSpecificFieldsToDTO(user, dto);

        // Self link
        dto.add(linkTo(methodOn(UserController.class)
                .getById(user.getId()))
                .withSelfRel());

        return dto;
    }

    public U toEntity(D dto) {
        U user = createEntityInstance();
        mapSharedFieldsToEntity(dto, user);
        mapSpecificFieldsToEntity(dto, user);
        return user;
    }

    protected void mapSharedFieldsToDTO(U user, D dto) {
        dto.setId(user.getId());
        dto.setUserType(user.getUserType());
        dto.setName(InputFormatter.normalizeName(user.getName()));
        dto.setLastname(InputFormatter.normalizeName(user.getLastname()));
        dto.setAge(user.getAge());
        dto.setDni(user.getDni());
        dto.setEmail(InputFormatter.normalizeEmail(user.getEmail()));
        dto.setProfilePic(user.getProfilePic());
    }

    protected void mapSharedFieldsToEntity(D dto, U user) {
        user.setId(dto.getId());
        user.setUserType(dto.getUserType());
        user.setName(InputFormatter.normalizeName(dto.getName()));
        user.setLastname(InputFormatter.normalizeName(dto.getLastname()));
        user.setAge(dto.getAge());
        user.setDni(dto.getDni());
        user.setEmail(InputFormatter.normalizeEmail(dto.getEmail()));
        user.setProfilePic(dto.getProfilePic());
    }
}
