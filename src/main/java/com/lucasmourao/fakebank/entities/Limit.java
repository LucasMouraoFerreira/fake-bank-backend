package com.lucasmourao.fakebank.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;

@Entity
@Table(name="tb_limit")
public class Limit implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Integer orderType;
	@NotNull
	private Integer accountType;
	@NotNull
	private Double amount;
	
	public Limit() {}

	public Limit(Long id, OrderType orderType, AccountType accountType, Double amount) {
		this.id = id;
		this.orderType = orderType.getCode();
		this.accountType = accountType.getCode();
		this.amount = amount;
	}

	public Long getId() {
		return id;
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

	public OrderType getOrderType() {
		if (orderType != null) {
			return OrderType.valueOf(orderType);
		}
		return null;
	}

	public void setOrderType(OrderType orderType) {
		if (orderType != null) {
			this.orderType = orderType.getCode();
		}
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
		Limit other = (Limit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
