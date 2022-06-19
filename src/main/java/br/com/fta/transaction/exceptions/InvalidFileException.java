package br.com.fta.transaction.exceptions;

public class InvalidFileException extends RuntimeException {
	
	private static final long serialVersionUID = 509716177110570102L;

	public InvalidFileException(String msg) {
		super(msg);
	}
}
