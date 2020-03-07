package com.lucasmourao.fakebank.entities;

import java.time.Instant;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.lucasmourao.fakebank.entities.enums.OrderType;

@Entity
public class LoanOrder extends Order {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Double rate;
	@NotNull
	private Double debtTotalAmount;
	@NotNull
	private Integer numberOfInstallments;
	@NotNull
	private Integer paidInstallments;
	@NotNull
	private Double amountPerInstallment;

	public LoanOrder() {
		super();
	}

	public LoanOrder(Long id, Instant moment, Double baseValue, Double fee, Account account, Double rate,
			Integer numberOfInstallments) {
		super(id, moment, OrderType.LOAN, baseValue, fee, account);
		this.rate = rate;
		setDebtTotalAmount();
		this.numberOfInstallments = numberOfInstallments;
		this.paidInstallments = 0;
		setAmountPerInstallment();
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getDebtTotalAmount() {
		return debtTotalAmount;
	}

	public void setDebtTotalAmount() {
		debtTotalAmount = (rate + 1) * super.getBaseValue();
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getPaidInstallments() {
		return paidInstallments;
	}

	public void incremetPaidInstallments() {
		this.paidInstallments++;
	}

	public Double getAmountPerInstallment() {
		return amountPerInstallment;
	}

	public void setAmountPerInstallment() {
		amountPerInstallment = debtTotalAmount / numberOfInstallments;
	}
	
}
