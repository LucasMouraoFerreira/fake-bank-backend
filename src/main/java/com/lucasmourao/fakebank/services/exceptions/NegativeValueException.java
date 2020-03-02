package com.lucasmourao.fakebank.services.exceptions;

public class NegativeValueException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NegativeValueException(String field) {
		super(field + " value must be a positive.");
	}

}
