package com.lucasmourao.fakebank.dto;

import java.io.Serializable;

public class AccountUpdateDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String ownerName;
	private String ownerCpf;
	private String address;
	private String password;
	
	public AccountUpdateDTO() {}

	public AccountUpdateDTO(String ownerName, String ownerCpf, String address, String password) {
		super();
		this.ownerName = ownerName;
		this.ownerCpf = ownerCpf;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
