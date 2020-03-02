package com.lucasmourao.fakebank.dto;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.enums.AccountType;

public class CompleteAccountDTO {

	private Long id;
	private Integer accountNumber;
	private Integer accountDigit;
	private Integer agency;
	private String ownerName;
	private String ownerCpf;
	private AccountType accountType;
	private String ownerAddress;
	private Double balance;
	private Boolean accountActive;
	private Double transferLimit;
	private Double loanLimitTotal;
	private Double loanLimitCurrent;
	private Double withdrawLimit;
	
	public CompleteAccountDTO() {}

	public CompleteAccountDTO(Account acc) {
		this.id = acc.getId();
		this.accountNumber = acc.getAccountNumber();
		this.accountDigit = acc.getAccountDigit();
		this.agency = acc.getAgency();
		this.ownerName = acc.getOwnerName();
		this.ownerCpf = acc.getOwnerCpf();
		this.accountType = acc.getAccountType();
		this.ownerAddress =acc.getOwnerAddress();
		this.balance =acc.getBalance();
		this.accountActive =acc.getAccountActive();
		this.transferLimit = acc.getTransferLimit();
		this.loanLimitTotal = acc.getLoanLimitTotal();
		this.loanLimitCurrent = acc.getLoanLimitCurrent();
		this.withdrawLimit = acc.getWithdrawLimit();
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

	public Double getLoanLimitTotal() {
		return loanLimitTotal;
	}

	public void setLoanLimitTotal(Double loanLimitTotal) {
		this.loanLimitTotal = loanLimitTotal;
	}

	public Double getTransferLimit() {
		return transferLimit;
	}

	public void setTransferLimit(Double transferLimit) {
		this.transferLimit = transferLimit;
	}

	public Double getLoanLimitCurrent() {
		return loanLimitCurrent;
	}

	public void setLoanLimitCurrent(Double loanLimitCurrent) {
		this.loanLimitCurrent = loanLimitCurrent;
	}

	public Double getWithdrawLimit() {
		return withdrawLimit;
	}

	public void setWithdrawLimit(Double withdrawLimit) {
		this.withdrawLimit = withdrawLimit;
	}
}
