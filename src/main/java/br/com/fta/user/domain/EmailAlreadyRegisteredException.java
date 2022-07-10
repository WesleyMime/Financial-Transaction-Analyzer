package br.com.fta.user.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyRegisteredException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 9154547206085357863L;
	
	public EmailAlreadyRegisteredException() {
		super("Email already registered.");
	}

}
