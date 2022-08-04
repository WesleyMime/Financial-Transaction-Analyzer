package br.com.fta.user.application;

import br.com.fta.user.domain.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailClient {

    void sendEmailWithPassword(@RequestBody UserDTO userDto);
}
