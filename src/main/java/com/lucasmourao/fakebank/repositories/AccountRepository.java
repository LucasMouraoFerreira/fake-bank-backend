package com.lucasmourao.fakebank.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmourao.fakebank.entities.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{

	public Page<Account> findAll(Pageable pageable);
	
}
