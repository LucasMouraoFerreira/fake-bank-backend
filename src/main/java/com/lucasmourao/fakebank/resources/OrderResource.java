package com.lucasmourao.fakebank.resources;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.services.AccountService;
import com.lucasmourao.fakebank.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService service;
	
	@Autowired AccountService accountService;

	@GetMapping
	public ResponseEntity<Page<Order>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Order> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable long id) {
		Order order = service.findById(id);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping(value = "/fullsearch")
	public ResponseEntity<Page<Order>> fullSearch(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "account_id") long account_id,
			@RequestParam(value = "orderType", defaultValue = "") OrderType orderType,
			@RequestParam(value = "initialDate", defaultValue = "") Instant initialDate,
			@RequestParam(value = "finalDate", defaultValue = "") Instant finalDate) {

		Page<Order> list;
		Pageable pageable = PageRequest.of(page, limit);
		Account account = accountService.findById(account_id);
		if(initialDate == null) {
			initialDate = Instant.parse("2020-01-01T00:00:00Z");
		}
		if(finalDate == null) {
			finalDate = Instant.now();
		}

		if(orderType == null) {
			list = service.fullSearch(account, initialDate, finalDate, pageable);
		}else {
			list = service.fullSearch(account, orderType.getCode(), initialDate, finalDate, pageable);
		}
		return ResponseEntity.ok().body(list);
	}
}
