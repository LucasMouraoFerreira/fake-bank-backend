package com.lucasmourao.fakebank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.repositories.AccountRepository;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	public List<Account> findAll() {
		return repository.findAll();
	}

	public Account findById(long id) {
		Optional<Account> acc = repository.findById(id);
		return acc.orElseThrow(()-> new ObjectNotFoundException(id));
	}

	public Account insertAccount(Account acc) {
		return repository.save(acc);
	}

	public void deleteById(long id) {
		try {
		repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id);
		}
	}

	public Account updateAccount(long id, Account acc) {
		Optional<Account> accAux = repository.findById(id);
		return updateData(accAux.orElseThrow(()-> new ObjectNotFoundException(id)), acc);
	}

	private Account updateData(Account accAux, Account acc) {

		if (acc.getOwnerAddress() != null) {
			accAux.setOwnerAddress(acc.getOwnerAddress());
		}
		if (acc.getOwnerCpf() != null) {
			accAux.setOwnerCpf(acc.getOwnerCpf());
		}
		if (acc.getOwnerName() != null) {
			accAux.setOwnerName(acc.getOwnerName());
		}
		if (acc.getPassword() != null) {
			accAux.setPassword(acc.getPassword());
		}
		if (acc.getAccountActive() != null) {
			accAux.setAccountActive(acc.getAccountActive());
		}

		return repository.save(accAux);
	}
}
