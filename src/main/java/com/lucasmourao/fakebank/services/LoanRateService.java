package com.lucasmourao.fakebank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.LoanRate;
import com.lucasmourao.fakebank.repositories.LoanRateRepository;
import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class LoanRateService {

	@Autowired
	private LoanRateRepository repository;
	
	public Page<LoanRate> findAll(Pageable pageable){
		return repository.findAll(pageable); 
	}
	
	public LoanRate findById(long id) {
		Optional<LoanRate> loanRate = repository.findById(id);
		return loanRate.orElseThrow(()-> new ObjectNotFoundException(id));
	}
	
	
	public LoanRate findLoanRate(Integer accountType){
		List<LoanRate> loanRates = repository.findLoanRate(accountType);
		if(loanRates.isEmpty()) {
			throw new ObjectNotFoundException(-1L);
		}
		else {
			return loanRates.get(0);
		}
	}
	
	public LoanRate insert(LoanRate loanRate) {
		return repository.save(loanRate);
	}
	
	public void delete(long id) {
		try {		
		repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public LoanRate updateLoanRate(long id, LoanRate loanRate) {
		Optional<LoanRate> loanRateAux = repository.findById(id);
		return updateData(loanRateAux.orElseThrow(()-> new ObjectNotFoundException(id)), loanRate);
	}
	
	private LoanRate updateData(LoanRate loanRateAux, LoanRate loanRate) {
		if(loanRate.getAccountType() !=null) {
			loanRateAux.setAccountType(loanRate.getAccountType());
		}
		if(loanRate.getRate()!=null && loanRate.getRate() > 0) {
			loanRateAux.setRate(loanRate.getRate());
		}
		return repository.save(loanRateAux);
	}
}
