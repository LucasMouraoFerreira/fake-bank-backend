package com.lucasmourao.fakebank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.repositories.FeeRepository;
import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
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
	
	public Fee insert(Fee fee) {
		return repository.save(fee);
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

	public Fee updateFee(long id, Fee fee) {
		Optional<Fee> feeAux = repository.findById(id);
		return updateData(feeAux.orElseThrow(()-> new ObjectNotFoundException(id)), fee);
	}
	
	private Fee updateData(Fee feeAux, Fee fee) {
		if(fee.getName()!=null) {
			feeAux.setName(fee.getName());
		}
		if(fee.getDescription()!=null) {
			feeAux.setDescription(fee.getDescription());
		}
		if(fee.getPercentage()!=null) {
			feeAux.setPercentage(fee.getPercentage());
		}
		if(fee.getTotalValue()!=null) {
			feeAux.setTotalValue(fee.getTotalValue());
		}
		if(fee.getOrderType()!=null) {
			feeAux.setOrderType(fee.getOrderType());
		}
		if(fee.getAccountType()!=null) {
			feeAux.setAccountType(fee.getAccountType());
		}
		return repository.save(feeAux);
	}
}
