package com.lucasmourao.fakebank.entities.enums;

public enum AccountType {

	STUDENT(0),
	STANDARD(1),
	PREMIUM(2);
	
	private int code;
	
	private AccountType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static AccountType valueOf(int code) {
		for (AccountType value : AccountType.values()) {
			if (code == value.getCode()) {
				return value;
			}
		}
		return null;
	}
}
