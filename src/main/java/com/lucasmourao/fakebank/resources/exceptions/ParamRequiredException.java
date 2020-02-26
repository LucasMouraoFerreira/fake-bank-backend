package com.lucasmourao.fakebank.resources.exceptions;

public class ParamRequiredException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ParamRequiredException(String msg) {
		super(msg);
	}
}
