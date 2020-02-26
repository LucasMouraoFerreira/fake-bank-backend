package com.lucasmourao.fakebank.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmourao.fakebank.entities.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{

	public Page<Account> findAll(Pageable pageable);
	
	public Page<Account> findByAgency(Integer agency,Pageable pageable);
	
	/*@Query("SELECT a FROM Account a where (a.ownerName = :ownerName)")
	Page<Account> searchByRatingTextRegion(@Param("ownerName") String ownerName);*/
	
}
