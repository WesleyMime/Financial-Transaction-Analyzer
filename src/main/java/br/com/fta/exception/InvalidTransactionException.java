package br.com.fta.exception;

public class InvalidTransactionException extends RuntimeException{
	
	private static final long serialVersionUID = 5661830732604961273L;
	
	public InvalidTransactionException(String msg) {
		super(msg);
	}

}
