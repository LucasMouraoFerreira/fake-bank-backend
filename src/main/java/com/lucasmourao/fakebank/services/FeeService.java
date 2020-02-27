package com.lucasmourao.fakebank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.repositories.FeeRepository;

@Service
public class FeeService {

	@Autowired
	private FeeRepository repository;
	
	public Page<Fee> findAll(Pageable pageable){
		return repository.findAll(pageable); 
	}
}
