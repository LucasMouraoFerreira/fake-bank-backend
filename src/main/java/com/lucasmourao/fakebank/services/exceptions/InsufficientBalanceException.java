package com.lucasmourao.fakebank.services.exceptions;

public class InsufficientBalanceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InsufficientBalanceException() {
		super("Insufficient balance");
	}

}
