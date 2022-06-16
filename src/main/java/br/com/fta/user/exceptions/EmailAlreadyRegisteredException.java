package br.com.fta.user.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 9154547206085357863L;
	
	public EmailAlreadyRegisteredException(String msg) {
		super(msg);
	}

}
