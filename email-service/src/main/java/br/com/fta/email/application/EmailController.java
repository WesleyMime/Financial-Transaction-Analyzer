package br.com.fta.email.application;

import br.com.fta.email.model.EmailService;
import br.com.fta.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailController {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-password", groupId = "email")
    public void sendEmailWithPassword(UserDTO user) {
        emailService.sendMessageWithPassword(user.name(), user.email(), user.password());
    }
}
