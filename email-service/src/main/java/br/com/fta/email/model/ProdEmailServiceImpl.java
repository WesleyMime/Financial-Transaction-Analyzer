package br.com.fta.email.model;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdEmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

	@Value("${MAIL_FROM}")
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
			System.out.println(e.getMessage());
		}
        emailSender.send(message);
		System.out.printf("%1$s E-mail sent to %2$s\n", subject, emailTo);
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
