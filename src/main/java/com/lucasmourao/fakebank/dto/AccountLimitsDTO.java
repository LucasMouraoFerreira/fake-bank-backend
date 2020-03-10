package com.lucasmourao.fakebank.dto;

import java.io.Serializable;

public class AccountLimitsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Double transferLimit;
	private Double loanLimitTotal;
	private Double withdrawLimit;
	
	public AccountLimitsDTO() {}

	public AccountLimitsDTO(Double transferLimit, Double loanLimitTotal, Double withdrawLimit) {
		this.transferLimit = transferLimit;
		this.loanLimitTotal = loanLimitTotal;
		this.withdrawLimit = withdrawLimit;
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

	public Double getWithdrawLimit() {
		return withdrawLimit;
	}

	public void setWithdrawLimit(Double withdrawLimit) {
		this.withdrawLimit = withdrawLimit;
	}
	
	
}
