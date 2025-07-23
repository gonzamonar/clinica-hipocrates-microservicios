package com.clinica_hipocrates.user_service.messaging.publisher;

import com.clinica_hipocrates.user_service.event.UserCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishUserCreatedEvent(UserCreatedEvent event) {
        rabbitTemplate.convertAndSend("user.exchange", "user.created", event);
    }
}
