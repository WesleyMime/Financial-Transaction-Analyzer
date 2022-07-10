package br.com.fta.transaction.domain;

import java.io.Serial;

public class InvalidFileException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = 509716177110570102L;

	public InvalidFileException(String msg) {
		super(msg);
	}
}
