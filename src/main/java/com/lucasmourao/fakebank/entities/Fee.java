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
@Table(name="tb_fee")
public class Fee implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	@NotNull
	private Double percentage;
	@NotNull
	private Double totalValue;
	@NotNull
	private Integer accountType;
	@NotNull
	private Integer orderType;

	public Fee() {
	}

	public Fee(Long id, String name, String description, Double percentage, Double totalValue, AccountType accountType,
			OrderType orderType) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.percentage = percentage;
		this.totalValue = totalValue;
		setAccountType(accountType);
		setOrderType(orderType);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
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
		Fee other = (Fee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
