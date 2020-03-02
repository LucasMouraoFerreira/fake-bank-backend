package com.lucasmourao.fakebank.dto;

import java.io.Serializable;

public class DepositOrderDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Double amount;
	private Integer accountNumber;
	private Integer accountDigit;
	private Integer agency;
	private String ownerName;
	private String password;
	
	public DepositOrderDTO() {}
	
	public DepositOrderDTO(Double amount, Integer accountNumber, Integer accountDigit, Integer agency, String ownerName,
			String password) {
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.accountDigit = accountDigit;
		this.agency = agency;
		this.ownerName = ownerName;
		this.password = password;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getAccountDigit() {
		return accountDigit;
	}

	public void setAccountDigit(Integer accountDigit) {
		this.accountDigit = accountDigit;
	}

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
