package com.lucasmourao.fakebank.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasmourao.fakebank.entities.enums.AccountType;

@Entity
@Table(name = "tb_account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Integer accountNumber;
	@NotNull
	private Integer accountDigit;
	@NotNull
	private Integer agency;
	@NotNull
	private Double balance;
	@NotNull
	private Integer accountType;
	@NotNull
	private Double transferLimit;
	@NotNull
	private Double loanLimitTotal;
	@NotNull
	private Double loanLimitCurrent;
	@NotNull
	private Double withdrawLimit;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Order> orders = new ArrayList<>();

	@OneToOne(mappedBy = "account")
    private User user;
	
	public Account() {
	}

	public Account(Long id, Integer accountNumber, Integer agency, Double balance, AccountType accountType, Integer accountDigit,
			Double transferLimit, Double loanLimitTotal, Double withdrawLimit) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.agency = agency;
		this.balance = balance;
		this.setAccountType(accountType);
		this.accountDigit = accountDigit;
		this.transferLimit = transferLimit;
		this.loanLimitCurrent = loanLimitTotal;
		this.loanLimitTotal = loanLimitTotal;
		this.withdrawLimit = withdrawLimit;
	}

	public Long getId() {
		return id;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		if (accountType != null) {
			return AccountType.valueOf(accountType);
		}
		return null;
	}

	public void setAccountType(AccountType accountType) {
		if (accountType != null) {
			this.accountType = accountType.getCode();
		}
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Integer getAccountDigit() {
		return accountDigit;
	}

	public void setAccountDigit(Integer accountDigit) {
		this.accountDigit = accountDigit;
	}

	public Double getTransferLimit() {
		return transferLimit;
	}

	public void setTransferLimit(Double transferLimit) {
		this.transferLimit = transferLimit;
	}

	public Double getLoanLimitTotal() {
		return loanLimitTotal;
	}

	public void setLoanLimitTotal(Double loanLimitTotal) {
		this.loanLimitTotal = loanLimitTotal;
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
	
	public User getUser() {
		return user;
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

	public void deposit(double amount) {
		balance += amount;
	}
	
	public void withdraw(double amount) {
		balance -= amount;
	}
	
	public void increaseLoanLimitCurrent(double amount) {
		loanLimitCurrent += amount;
		if(loanLimitCurrent>loanLimitTotal) {
			loanLimitCurrent = loanLimitTotal;
		}
	}
	
	public void decreaseLoanLimitCurrent(double amount) {
		loanLimitCurrent -= amount;
	}
}
