package br.com.fta.user.infra;

import br.com.fta.user.domain.UserDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Profile("test")
public class EmailClientTest implements EmailClient {

    public void sendEmailWithPassword(@RequestBody UserDTO userDto) {}
}
