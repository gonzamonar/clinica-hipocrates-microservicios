package com.clinica_hipocrates.user_service.assembler;

import com.clinica_hipocrates.common.util.InputFormatter;
import com.clinica_hipocrates.user_service.controller.UserController;
import com.clinica_hipocrates.user_service.dto.SpecialityDTO;
import com.clinica_hipocrates.user_service.dto.UserRequestDTO;
import com.clinica_hipocrates.user_service.dto.UserResponseDTO;
import com.clinica_hipocrates.user_service.model.Speciality;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.model.UserType;
import com.clinica_hipocrates.user_service.repository.SpecialityRepository;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDTOAssembler
        extends RepresentationModelAssemblerSupport<User, UserResponseDTO> {

    private final SpecialityRepository specialityRepository;
    private final SpecialityDTOAssembler specialityAssembler;


    public UserDTOAssembler(
            SpecialityRepository specialityRepository,
            SpecialityDTOAssembler specialityAssembler) {
        super(UserController.class, UserResponseDTO.class);
        this.specialityRepository = specialityRepository;
        this.specialityAssembler = specialityAssembler;
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        mapSharedFieldsToEntity(user, dto);
        mapSpecificFieldsToEntity(user, dto);
        return user;
    }

    public UserResponseDTO toModel(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        mapSharedFieldsToDTO(user, dto);
        mapSpecificFieldsToDTO(user, dto);

        // Self link
        dto.add(linkTo(methodOn(UserController.class)
                .getById(user.getId()))
                .withSelfRel());

        List<SpecialityDTO> specialityDTOs = user.getSpecialities()
                .stream()
                .map(specialityAssembler::toModel)
                .toList();

        dto.setSpecialities(specialityDTOs);

        return dto;
    }

    public List<UserResponseDTO> toModelList(List<User> users) {
        return users.stream().map(this::toModel).toList();
    }

    public List<User> toEntityList(List<UserRequestDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }


    // ENTITY TO RESPONSE
    private void mapSharedFieldsToDTO(User user, UserResponseDTO dto) {
        dto.setId(user.getId());
        dto.setUserType(user.getUserType());
        dto.setName(InputFormatter.normalizeName(user.getName()));
        dto.setLastname(InputFormatter.normalizeName(user.getLastname()));
        dto.setAge(user.getAge());
        dto.setDni(user.getDni());
        dto.setEmail(InputFormatter.normalizeEmail(user.getEmail()));
        dto.setProfilePic(user.getProfilePic());
    }

    private void mapSpecificFieldsToDTO(User user, UserResponseDTO dto) {
        switch (user.getUserType()) {
            case UserType.ESPECIALISTA -> dto.setSpecialities(specialityAssembler.toModelList(user.getSpecialities()));
            case UserType.PACIENTE -> {
                dto.setHealthInsurance(user.getHealthInsurance());
                dto.setProfilePicAlt(user.getProfilePicAlt());
            }
        }
    }


    // REQUEST TO ENTITY
    private void mapSharedFieldsToEntity(User user, UserRequestDTO dto) {
        user.setId(dto.getId());
        user.setUserType(dto.getUserType());
        user.setEnabled(dto.getEnabled());
        user.setName(InputFormatter.normalizeName(dto.getName()));
        user.setLastname(InputFormatter.normalizeName(dto.getLastname()));
        user.setAge(dto.getAge());
        user.setDni(dto.getDni());
        user.setEmail(InputFormatter.normalizeEmail(dto.getEmail()));
        user.setProfilePic(dto.getProfilePic());
    }

    private void mapSpecificFieldsToEntity(User user, UserRequestDTO dto) {
        switch (user.getUserType()) {
            case UserType.ESPECIALISTA -> {
                if (dto.getSpecialities() != null && !dto.getSpecialities().isEmpty()) {
                    List<Speciality> specialities = specialityRepository.findAllById(dto.getSpecialities());
                    user.setSpecialities(specialities);
                }
            }
            case UserType.PACIENTE -> {
                user.setHealthInsurance(dto.getHealthInsurance());
                user.setProfilePicAlt(dto.getProfilePicAlt());
            }
        }
    }



    // FOR TESTING PURPOSES ONLY
    public User responseToEntity(UserResponseDTO dto) {
        User user = new User();
        mapResponseToEntity(user, dto);
        return user;
    }

    public List<User> responsetoEntityList(List<UserResponseDTO> dtos) {
        return dtos.stream().map(this::responseToEntity).toList();
    }

    private void mapResponseToEntity(User user, UserResponseDTO dto) {
        user.setId(dto.getId());
        user.setUserType(dto.getUserType());
        user.setEnabled(true);
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setAge(dto.getAge());
        user.setDni(dto.getDni());
        user.setEmail(dto.getEmail());
        user.setProfilePic(dto.getProfilePic());
        switch (user.getUserType()) {
            case UserType.ESPECIALISTA -> user.setSpecialities(specialityAssembler.toEntityList(dto.getSpecialities()));
            case UserType.PACIENTE -> {
                user.setHealthInsurance(dto.getHealthInsurance());
                user.setProfilePicAlt(dto.getProfilePicAlt());
            }
        }
    }
}
