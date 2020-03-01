package com.lucasmourao.fakebank.services.exceptions;

public class FieldRequiredException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FieldRequiredException(String field) {
		super("Field " + field + " is required.");
	}

}
