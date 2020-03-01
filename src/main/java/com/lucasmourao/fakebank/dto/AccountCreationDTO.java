package com.lucasmourao.fakebank.dto;

import java.io.Serializable;

public class AccountCreationDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String password;
	private String ownerName;
	private String ownerCpf;
	private String ownerAddress;
	private Integer accountType;

	public AccountCreationDTO() {}

	public AccountCreationDTO(String password, String ownerName, String ownerCpf, String ownerAddress, Double balance,
			Integer accountType) {
		this.password = password;
		this.ownerName = ownerName;
		this.ownerCpf = ownerCpf;
		this.ownerAddress = ownerAddress;
		this.accountType = accountType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}	
	
}
