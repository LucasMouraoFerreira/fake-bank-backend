package com.lucasmourao.fakebank.dto;

import java.io.Serializable;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.enums.AccountType;

public class SimpleAccountDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer accountNumber;
	private Integer accountDigit;
	private Integer agency;
	private String ownerName;
	private String ownerCpf;
	private AccountType accountType;
	
	public SimpleAccountDTO() {}

	public SimpleAccountDTO(Account acc) {
		this.id = acc.getId();
		this.accountNumber = acc.getAccountNumber();
		this.accountDigit = acc.getAccountDigit();
		this.agency = acc.getAgency();
		this.ownerName = acc.getOwnerName();
		this.ownerCpf = acc.getOwnerCpf();
		this.accountType = acc.getAccountType();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOwnerCpf() {
		return ownerCpf;
	}

	public void setOwnerCpf(String ownerCpf) {
		this.ownerCpf = ownerCpf;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}
