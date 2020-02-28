package com.lucasmourao.fakebank.entities.enums;

public enum OrderType {

	DEPOSIT(0),
	WITHDRAW(1),
	TRANSFER(2),
	LOAN(3),
	CARD_ANNUITY(4),
	MONTHLY_FEE(5);

	private int code;
	
	private OrderType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static OrderType valueOf(int code) {
		for(OrderType value : OrderType.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		return null;
	}
	
}
