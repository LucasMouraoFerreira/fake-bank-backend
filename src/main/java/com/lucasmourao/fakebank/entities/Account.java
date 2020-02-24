package com.lucasmourao.fakebank.entities;

import java.io.Serializable;

public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer accountNumber;
	private Integer agency;
	private Integer password;
	private String ownerName;
	private String ownerCpf;
	private String ownerAddress;
	private Double balance;
	private Boolean accountActive;
	
	public Account() {}
	
	public Account(Long id, Integer accountNumber, Integer agency, Integer password, String ownerName, String ownerCpf,
			String ownerAddress, Double balance, Boolean accountActive) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.agency = agency;
		this.password = password;
		this.ownerName = ownerName;
		this.ownerCpf = ownerCpf;
		this.ownerAddress = ownerAddress;
		this.balance = balance;
		this.accountActive = accountActive;
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

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean getAccountActive() {
		return accountActive;
	}

	public void setAccountActive(Boolean accountActive) {
		this.accountActive = accountActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
