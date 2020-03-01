package com.lucasmourao.fakebank.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.fakebank.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Page<Account> findAll(Pageable pageable);

	public Page<Account> findByAgency(Integer agency, Pageable pageable);

	@Query("SELECT a FROM Account a where ((UPPER(a.ownerName) like UPPER(concat('%', :ownerName,'%'))) or (a.ownerCpf = :ownerCpf) or (a.accountNumber = :accountNumber and a.accountDigit = :accountDigit and a.agency = :agency))")
	Page<Account> fullSearch(@Param("ownerName") String ownerName, @Param("ownerCpf") String ownerCpf,
			@Param("accountNumber") Integer accountNumber, @Param("accountDigit") Integer accountDigit,
			@Param("agency") Integer agency, Pageable pageable);
	
	@Query("SELECT a FROM Account a where a.accountNumber = :accountNumber and a.accountDigit = :accountDigit and a.agency = :agency")
	List<Account> findAccount(@Param("accountNumber") Integer accountNumber, @Param("accountDigit") Integer accountDigit,
			@Param("agency") Integer agency);

}
