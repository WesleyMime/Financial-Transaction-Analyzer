package br.com.fta.user.application;

import br.com.fta.user.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Profile({"dev", "default", "prod"})
public class EmailClientKafka implements EmailClient{

    @Autowired
    private KafkaTemplate<String, UserDTO> kafkaTemplate;

    public void sendEmailWithPassword(@RequestBody UserDTO userDto) {
        kafkaTemplate.send("email-password", userDto);
    }
}
