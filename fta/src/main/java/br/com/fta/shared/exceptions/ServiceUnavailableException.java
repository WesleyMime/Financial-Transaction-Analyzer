package br.com.fta.shared.exceptions;

import java.io.Serial;

public class ServiceUnavailableException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -1434589728486924180L;

	public ServiceUnavailableException(String msg) {
		super(msg);
	}
}
