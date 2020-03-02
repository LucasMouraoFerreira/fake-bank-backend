package com.lucasmourao.fakebank.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasmourao.fakebank.entities.enums.OrderType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="tb_order")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private Instant moment;
	@NotNull
	private Integer orderType;
	@NotNull
	private Double baseValue;
	@NotNull
	private Double fee;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="account_id", nullable=false)
	private Account account;
	
	public Order() {
	}

	public Order(Long id, Instant moment, OrderType orderType, Double baseValue, Double fee, Account account) {
		this.id = id;
		this.moment = moment;
		setOrderType(orderType);
		this.baseValue = baseValue;
		this.fee = fee;
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
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

	public Double getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(Double baseValue) {
		this.baseValue = baseValue;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
