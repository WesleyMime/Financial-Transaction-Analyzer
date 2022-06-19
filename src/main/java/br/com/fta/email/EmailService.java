package br.com.fta.email;

public interface EmailService {

	void sendMessage(String emailTo, String subject, String text);

	void sendMessageWithPassword(String name, String email, String password);
}
