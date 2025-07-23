package com.clinica_hipocrates.user_service.messaging.listener;

import com.clinica_hipocrates.user_service.assembler.UserDTOAssembler;
import com.clinica_hipocrates.user_service.dto.*;
import com.clinica_hipocrates.user_service.event.UserRegisteredEvent;
import com.clinica_hipocrates.user_service.messaging.RabbitMQConfig;
import com.clinica_hipocrates.user_service.model.User;
import com.clinica_hipocrates.user_service.model.UserType;
import com.clinica_hipocrates.user_service.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventListener {

    private final UserService userService;
    private final UserDTOAssembler assembler;

    public UserRegisteredEventListener(
            UserService userService,
            UserDTOAssembler assembler
    ) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTERED_QUEUE)
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        try {
            System.out.println("EVENT");
            System.out.println(event);
            UserRequestDTO dto = createUserDTOFromEvent(event);
            User user = assembler.toEntity(dto);
            userService.create(user);
        } catch (Exception e) {
            // publish failed event
            System.out.println("EXCEPTION");
            System.out.println(e);
        }
    }

    private UserRequestDTO createUserDTOFromEvent(UserRegisteredEvent event) {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setId(event.getId());
        dto.setUserType(event.getUserType());
        dto.setName(event.getName());
        dto.setLastname(event.getLastname());
        dto.setAge(event.getAge());
        dto.setDni(event.getDni());
        dto.setEmail(event.getEmail());
        dto.setProfilePic(event.getProfilePic());
        if (event.getUserType().equals(UserType.ESPECIALISTA)) {
            dto.setSpecialities(event.getSpecialityIds().stream().toList());
        }

        if (event.getUserType().equals(UserType.PACIENTE)) {
            dto.setHealthInsurance(event.getHealthInsurance());
            dto.setProfilePicAlt(event.getProfilePicAlt());
        }
        return dto;
    }
}
