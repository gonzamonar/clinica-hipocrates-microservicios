package com.clinica_hipocrates.auth_service.messaging.publisher;

import com.clinica_hipocrates.auth_service.event.UserRegisteredEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        rabbitTemplate.convertAndSend("user.exchange", "user.registered", event);
    }
}
