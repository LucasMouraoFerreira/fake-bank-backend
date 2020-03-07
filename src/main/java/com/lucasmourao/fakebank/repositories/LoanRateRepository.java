package com.lucasmourao.fakebank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmourao.fakebank.entities.LoanRate;

public interface LoanRateRepository extends JpaRepository<LoanRate, Long> {
	
}
