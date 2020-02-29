package com.lucasmourao.fakebank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	public Page<Order> findAll(Pageable pageable){
		return repository.findAll(pageable);
	}
}
