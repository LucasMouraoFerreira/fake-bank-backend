package com.lucasmourao.fakebank.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.LoanOrder;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.entities.TransferOrder;
import com.lucasmourao.fakebank.repositories.OrderRepository;
import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	public Order findById(long id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.orElseThrow(() -> new ObjectNotFoundException(id));
	}

	public Page<Order> fullSearch(Account account, Integer orderType, Instant initialDate, Instant finalDate,
			Pageable pageable) {
		return orderRepository.fullSearch(account, orderType, initialDate, finalDate, pageable);
	}

	public Page<Order> fullSearch(Account account, Instant initialDate, Instant finalDate, Pageable pageable) {
		return orderRepository.fullSearch(account, initialDate, finalDate, pageable);
	}
	
	public List<Order> findByOrderType(Integer orderType){
		return orderRepository.findByOrderType(orderType);
	}

	public Order insert(Order order) {
		return orderRepository.save(order);
	}

	public LoanOrder insert(LoanOrder order) {
		return orderRepository.save(order);
	}

	public TransferOrder insert(TransferOrder order) {
		return orderRepository.save(order);
	}

	public void delete(long id) {
		try {
			orderRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void saveLoanOrder(LoanOrder order) {
		orderRepository.save(order);
	}

}
