package br.com.fta.email.application;

import br.com.fta.email.model.EmailService;
import br.com.fta.email.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email-with-password")
    public void sendEmailWithPassword(@RequestBody UserDTO user) {
        emailService.sendMessageWithPassword(user.name(), user.email(), user.password());
    }
}
