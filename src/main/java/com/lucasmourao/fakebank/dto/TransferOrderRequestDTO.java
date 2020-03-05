package com.lucasmourao.fakebank.dto;

public class TransferOrderRequestDTO extends OrderRequestDTO{

	private static final long serialVersionUID = 1L;

	private Integer receivingAccountNumber;
	private Integer receivingAccountDigit;
	private Integer receivingAccountAgency;
	private String receivingAccountOwnerCpf;
	
	public TransferOrderRequestDTO() {}

	public TransferOrderRequestDTO(Double amount, Integer accountNumber, Integer accountDigit, Integer agency,
			String ownerName, String password, Integer receivingAccountNumber, Integer receivingAccountDigit,
			Integer receivingAccountAgency, String receivingAccountOwnerCpf) {
		super(amount, accountNumber, accountDigit, agency, ownerName, password);
		this.receivingAccountNumber = receivingAccountNumber;
		this.receivingAccountDigit = receivingAccountDigit;
		this.receivingAccountAgency = receivingAccountAgency;
		this.setReceivingAccountOwnerCpf(receivingAccountOwnerCpf);
	}

	public Integer getReceivingAccountNumber() {
		return receivingAccountNumber;
	}

	public void setReceivingAccountNumber(Integer receivingAccountNumber) {
		this.receivingAccountNumber = receivingAccountNumber;
	}

	public Integer getReceivingAccountDigit() {
		return receivingAccountDigit;
	}

	public void setReceivingAccountDigit(Integer receivingAccountDigit) {
		this.receivingAccountDigit = receivingAccountDigit;
	}

	public Integer getReceivingAccountAgency() {
		return receivingAccountAgency;
	}

	public void setReceivingAccountAgency(Integer receivingAccountAgency) {
		this.receivingAccountAgency = receivingAccountAgency;
	}

	public String getReceivingAccountOwnerCpf() {
		return receivingAccountOwnerCpf;
	}

	public void setReceivingAccountOwnerCpf(String receivingAccountOwnerCpf) {
		this.receivingAccountOwnerCpf = receivingAccountOwnerCpf;
	}
		
}
