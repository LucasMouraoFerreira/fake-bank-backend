package com.lucasmourao.fakebank.entities;

import java.time.Instant;

import javax.persistence.Entity;

import com.lucasmourao.fakebank.entities.enums.OrderType;

@Entity
public class TrasferOrder extends Order {

	private static final long serialVersionUID = 1L;

	private Long receivingAccountId;
	
	public TrasferOrder() {}

	public TrasferOrder(Long id, Instant moment, OrderType orderType, Double baseValue, Double fee, Account account,
			Long receivingAccountId) {
		super(id, moment, orderType, baseValue, fee, account);
		this.receivingAccountId = receivingAccountId;
	}

	public Long getReceivingAccountId() {
		return receivingAccountId;
	}

	public void setReceivingAccountId(Long receivingAccountId) {
		this.receivingAccountId = receivingAccountId;
	}	
	
	
}
