package br.com.fta.email.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Profile("prod")
public class ProdEmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    @Override
    public void sendMessage(String emailTo, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        
       	MimeMessageHelper helper = new MimeMessageHelper(message);
       	try {
       		helper.setFrom("Financial Transaction Analyzer <" + this.EMAIL_FROM + ">");
	       	helper.setTo(emailTo); 
	       	helper.setSubject(subject); 
	       	helper.setText(text);
       	} catch (MessagingException e) {
			e.printStackTrace();
		}
        emailSender.send(message);
    }

	@Override
	public void sendMessageWithPassword(String name, String email, String password) {
		String subject = "Password";
		
		String content = "Hello, " + name + "!\n" +
				"Your password to access the application is: " + password + ".\n" +
				"If you don't know why you are receiving this email, you can just ignore it.";
		
		sendMessage(email, subject, content);
	}
    
}
