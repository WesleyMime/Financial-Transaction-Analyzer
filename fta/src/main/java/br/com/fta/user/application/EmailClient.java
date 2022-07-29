package br.com.fta.user.application;

import br.com.fta.user.domain.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("email-service")
@Profile({"dev", "default", "prod"})
public interface EmailClient {

    @PostMapping("/send-email-with-password")
    void sendEmailWithPassword(@RequestBody UserDTO userDto);
}
