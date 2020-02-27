package com.lucasmourao.fakebank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.repositories.FeeRepository;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class FeeService {

	@Autowired
	private FeeRepository repository;
	
	public Page<Fee> findAll(Pageable pageable){
		return repository.findAll(pageable); 
	}
	
	public Fee findById(long id) {
		Optional<Fee> fee = repository.findById(id);
		return fee.orElseThrow(()-> new ObjectNotFoundException(id));
	}
	
	public Page<Fee> findByName(String name, Pageable pageable){
		return repository.findByName(name, pageable);
	}
	
	public Page<Fee> findByNameAndAccountType(String name, Integer accountType, Pageable pageable){
		return repository.findByNameAndAccountType(name, accountType, pageable);
	}
	
	public Page<Fee> findByNameAndOrderType(String name, Integer orderType, Pageable pageable){
		return repository.findByNameAndOrderType(name, orderType, pageable);
	}
	
	public Page<Fee> fullSearch(String name, Integer orderType ,Integer accountType, Pageable pageable){
		return repository.fullSearch(name, orderType, accountType, pageable);
	}
}
