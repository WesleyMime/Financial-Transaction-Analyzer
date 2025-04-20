package br.com.fta.user.infra;

import br.com.fta.user.domain.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailClient {

    // Email Service will log the password when on "dev" profile.
    void sendEmailWithPassword(@RequestBody UserDTO userDto);
}
