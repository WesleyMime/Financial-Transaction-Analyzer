package br.com.fta.user.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 9154547206085357863L;
	
	public EmailAlreadyRegisteredException() {
		super("Email already registered.");
	}

}
