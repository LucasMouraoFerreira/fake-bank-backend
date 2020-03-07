package com.lucasmourao.fakebank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.lucasmourao.fakebank.entities.LoanRate;

public interface LoanRateRepository extends JpaRepository<LoanRate, Long> {
	
	@Query("SELECT l FROM LoanRate l where l.accountType = :accountType")
	List<LoanRate> findLoanRate(@Param("accountType") Integer accountType);
}
