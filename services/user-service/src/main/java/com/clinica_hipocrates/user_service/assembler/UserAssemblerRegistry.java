package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.user_service.dto.AbstractUserDTO;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.model.UserType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAssemblerRegistry {
    private final AdminDTOAssembler adminDTOAssembler;
    private final SpecialistDTOAssembler specialistDTOAssembler;
    private final PatientDTOAssembler patientDTOAssembler;

    public UserAssemblerRegistry(AdminDTOAssembler adminDTOAssembler,
                                 SpecialistDTOAssembler specialistDTOAssembler,
                                 PatientDTOAssembler patientDTOAssembler) {
        this.adminDTOAssembler = adminDTOAssembler;
        this.specialistDTOAssembler = specialistDTOAssembler;
        this.patientDTOAssembler = patientDTOAssembler;
    }

    private AbstractUserDTOAssembler<? extends User, ? extends AbstractUserDTO> getAssembler(UserType type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("UserType must not be null");
        }

        // This switch is fully test covered except for default / unknown Enum values,
        // which are not possible to simulate due to Enum constraints in Java.
        return switch (type) {
            case ADMIN -> adminDTOAssembler;
            case PACIENTE -> patientDTOAssembler;
            case ESPECIALISTA -> specialistDTOAssembler;
            default -> throw new IllegalArgumentException("Unsupported user type: " + type);
        };
    }

    public AbstractUserDTO toDto(User user) {
        return toDtoInternal(user);
    }

    private <U extends User, D extends AbstractUserDTO> D toDtoInternal(U user) {
        @SuppressWarnings("unchecked")
        UserAssembler<U, D> assembler = (UserAssembler<U, D>) getAssembler(user.getUserType());
        return assembler.toModel(user);
    }

    public List<AbstractUserDTO> toDtoList(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

    public User toEntity(AbstractUserDTO dto) {
        return toEntityInternal(dto);
    }

    private <U extends User, D extends AbstractUserDTO> U toEntityInternal(D dto) {
        @SuppressWarnings("unchecked")
        UserAssembler<U, D> assembler = (UserAssembler<U, D>) getAssembler(dto.getUserType());
        return assembler.toEntity(dto);
    }

    public List<User> toEntityList(List<AbstractUserDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
