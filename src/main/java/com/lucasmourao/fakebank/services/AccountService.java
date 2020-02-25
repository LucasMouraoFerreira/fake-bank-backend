package com.lucasmourao.fakebank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.repositories.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository repository;
	
	public List<Account> findAll(){
		return repository.findAll();
	}
	
	public Account findById(long id){
		Optional<Account> acc = repository.findById(id);
		return acc.get();
	}
}
