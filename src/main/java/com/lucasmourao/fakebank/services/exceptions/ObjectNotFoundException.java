package com.lucasmourao.fakebank.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(long id) {
		super("Object not found. Id: " + id);
	}

}
