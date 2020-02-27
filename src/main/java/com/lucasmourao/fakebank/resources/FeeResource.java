package com.lucasmourao.fakebank.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.services.FeeService;

@RestController
@RequestMapping(value = "/fees")
public class FeeResource {

	@Autowired
	private FeeService service;

	@GetMapping
	public ResponseEntity<Page<Fee>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Fee> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Fee> findById(@PathVariable long id) {
		Fee fee = service.findById(id);
		return ResponseEntity.ok().body(fee);
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<Page<Fee>> fullSearch(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "feeountType", defaultValue = "") AccountType accountType,
			@RequestParam(value = "orderType", defaultValue = "") OrderType orderType) {

		Page<Fee> list;
		Pageable pageable = PageRequest.of(page, limit);

		if(accountType == null && orderType == null) {
			list = service.findByName(name, pageable);
		}else if (accountType == null) {
			list = service.findByNameAndOrderType(name, orderType.getCode(), pageable);
		} else if(orderType == null) {
			list = service.findByNameAndAccountType(name, accountType.getCode(), pageable);
		} else {
			list = service.fullSearch(name, orderType.getCode(), accountType.getCode(), pageable);
		}
		
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Fee> insertFee(@RequestBody Fee fee) {
		fee = service.insert(fee);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fee.getId())
				.toUri();
		return ResponseEntity.created(location).body(fee);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteFee(@PathVariable long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Fee> updateFee(@PathVariable long id, @RequestBody Fee fee) {
		fee = service.updateFee(id, fee);
		return ResponseEntity.ok().body(fee);
	}

}
