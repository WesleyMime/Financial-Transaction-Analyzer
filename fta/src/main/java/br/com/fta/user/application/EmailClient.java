package br.com.fta.user.application;

import br.com.fta.user.domain.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("email-service")
public interface EmailClient {

    @PostMapping("/send-email-with-password")
    void sendEmailWithPassword(@RequestBody UserDTO userDto);
}
