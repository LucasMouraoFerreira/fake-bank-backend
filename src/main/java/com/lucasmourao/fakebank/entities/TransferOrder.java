package com.lucasmourao.fakebank.entities;

import java.time.Instant;

import javax.persistence.Entity;

import com.lucasmourao.fakebank.entities.enums.OrderType;

@Entity
public class TransferOrder extends Order {

	private static final long serialVersionUID = 1L;

	private Long receivingAccountId;
	
	public TransferOrder() {
		super();
	}

	public TransferOrder(Long id, Instant moment, Double baseValue, Double fee, Account account,
			Long receivingAccountId) {
		super(id, moment, OrderType.TRANSFER, baseValue, fee, account);
		this.receivingAccountId = receivingAccountId;
	}

	public Long getReceivingAccountId() {
		return receivingAccountId;
	}

	public void setReceivingAccountId(Long receivingAccountId) {
		this.receivingAccountId = receivingAccountId;
	}	
	
	
}
