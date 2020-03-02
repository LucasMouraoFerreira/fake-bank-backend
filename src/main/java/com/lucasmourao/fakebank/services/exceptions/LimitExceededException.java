package com.lucasmourao.fakebank.services.exceptions;

public class LimitExceededException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public LimitExceededException(String orderType) {
		super(orderType + " limit exceeded.");
	}

}
