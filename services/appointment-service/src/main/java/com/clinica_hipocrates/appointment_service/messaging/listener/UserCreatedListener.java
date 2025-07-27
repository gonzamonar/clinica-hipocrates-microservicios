package com.clinica_hipocrates.appointment_service.messaging.listener;

import com.clinica_hipocrates.appointment_service.event.UserCreatedEvent;
import com.clinica_hipocrates.appointment_service.messaging.RabbitMQConfig;
import com.clinica_hipocrates.appointment_service.model.CachedUser;
import com.clinica_hipocrates.appointment_service.repository.CachedUserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserCreatedListener {

    private final CachedUserRepository cachedUserRepository;

    public UserCreatedListener(CachedUserRepository cachedUserRepository) {
        this.cachedUserRepository = cachedUserRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    public void handleUserCreated(UserCreatedEvent event) {
        CachedUser user = new CachedUser();
        user.setId(event.getId());
        user.setEnabled(event.getEnabled());
        user.setUserType(event.getUserType());
        user.setName(event.getName());
        user.setLastname(event.getLastname());
        user.setSpecialityIds(event.getSpecialityIds());
        user.setLastUpdated(LocalDateTime.now());
        cachedUserRepository.save(user);
    }
}
