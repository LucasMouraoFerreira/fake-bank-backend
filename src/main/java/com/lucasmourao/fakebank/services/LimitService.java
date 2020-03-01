package com.lucasmourao.fakebank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Limit;
import com.lucasmourao.fakebank.repositories.LimitRepository;
import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class LimitService {

	@Autowired
	private LimitRepository repository;
	
	public Page<Limit> findAll(Pageable pageable){
		return repository.findAll(pageable); 
	}
	
	public Limit findById(long id) {
		Optional<Limit> limit = repository.findById(id);
		return limit.orElseThrow(()-> new ObjectNotFoundException(id));
	}
	
	public Limit findLimit(Integer accountType, Integer orderType){
		List<Limit> limits = repository.findLimit(accountType,orderType);
		if(limits.isEmpty()) {
			throw new ObjectNotFoundException(-1L);
		}
		else {
			return limits.get(0);
		}
	}
	
	public Limit insert(Limit limit) {
		return repository.save(limit);
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

	public Limit updateLimit(long id, Limit limit) {
		Optional<Limit> limitAux = repository.findById(id);
		return updateData(limitAux.orElseThrow(()-> new ObjectNotFoundException(id)), limit);
	}
	
	private Limit updateData(Limit limitAux, Limit limit) {
		if(limit.getAmount()!=null) {
			limitAux.setAmount(limit.getAmount());
		}
		return repository.save(limitAux);
	}
}
