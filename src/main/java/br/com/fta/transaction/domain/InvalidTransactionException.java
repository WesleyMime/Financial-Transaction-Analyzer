package br.com.fta.transaction.domain;

public class InvalidTransactionException extends RuntimeException{
	
	private static final long serialVersionUID = 5661830732604961273L;
	
	public InvalidTransactionException() {
		super("Invalid transaction.");
	}
	
	public InvalidTransactionException(String msg) {
		super(msg);
	}

}
