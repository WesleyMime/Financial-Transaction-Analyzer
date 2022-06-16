package br.com.fta.user;

import java.util.Random;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
		// Default password is a number between 100000 and 999999
		// e.g. 123999
		this.password = String.valueOf(new Random().nextInt(100000, 1000000));
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
}
