package br.com.fta.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test", "default", "dev"})
public class DevTestEmailServiceImpl implements EmailService {

    public void sendMessage(String emailTo, String subject, String text) {
       	System.out.println("Email to: " + emailTo + ", subject: " + subject + ", content: " + text);
    }

	@Override
	public void sendMessageWithPassword(String name, String email, String password) {
		System.out.println("Name: " + name + ", email: " + email + ", password: " + password);
		
	}
}
