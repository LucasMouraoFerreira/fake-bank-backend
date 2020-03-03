package com.lucasmourao.fakebank.dto;

public class LoanOrderRequestDTO extends OrderRequestDTO{

	private static final long serialVersionUID = 1L;

	private Integer numberOfInstallments;
	
	public LoanOrderRequestDTO() {}

	public LoanOrderRequestDTO(Double amount, Integer accountNumber, Integer accountDigit, Integer agency,
			String ownerName, String password, Integer numberOfInstallments) {
		super(amount, accountNumber, accountDigit, agency, ownerName, password);
		this.setNumberOfInstallments(numberOfInstallments);
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}	
}
