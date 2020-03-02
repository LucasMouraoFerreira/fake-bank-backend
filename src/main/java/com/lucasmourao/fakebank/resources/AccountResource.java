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

import com.lucasmourao.fakebank.dto.AccountCreationDTO;
import com.lucasmourao.fakebank.dto.CompleteAccountDTO;
import com.lucasmourao.fakebank.dto.OrderRequestDTO;
import com.lucasmourao.fakebank.dto.SimpleAccountDTO;
import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.services.AccountService;

@RestController
@RequestMapping(value = "/accounts")
public class AccountResource {

	@Autowired
	private AccountService service;

	@GetMapping
	public ResponseEntity<Page<SimpleAccountDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<SimpleAccountDTO> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CompleteAccountDTO> findById(@PathVariable long id) {
		CompleteAccountDTO acc = new CompleteAccountDTO(service.findById(id));
		return ResponseEntity.ok().body(acc);
	}

	@GetMapping(value = "/agencysearch")
	public ResponseEntity<Page<SimpleAccountDTO>> findbyAgency(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit, @RequestParam(value = "agency") int agency) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<SimpleAccountDTO> list = service.findByAgency(agency, pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<Page<SimpleAccountDTO>> fullSearch(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "accountNumber", defaultValue = "0") Integer accountNumber,
			@RequestParam(value = "accountDigit", defaultValue = "0") Integer accountDigit,
			@RequestParam(value = "agency", defaultValue = "1000") Integer agency,
			@RequestParam(value = "ownerName", defaultValue = "") String ownerName,
			@RequestParam(value = "ownerCpf", defaultValue = "") String ownerCpf) {

		Pageable pageable = PageRequest.of(page, limit);
		Page<SimpleAccountDTO> list = service.fullSearch(ownerName, ownerCpf, accountNumber, accountDigit,agency, pageable);
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<CompleteAccountDTO> insertAccount(@RequestBody AccountCreationDTO acc) {
		CompleteAccountDTO accAux = new CompleteAccountDTO(service.insertAccount(acc));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(accAux.getId())
				.toUri();
		return ResponseEntity.created(location).body(accAux);
	}
	
	@PostMapping(value="/deposit")
	public ResponseEntity<Order> deposit(@RequestBody OrderRequestDTO depositOrder){
		Order order = service.deposit(depositOrder);
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping(value="/withdraw")
	public ResponseEntity<Order> withdraw(@RequestBody OrderRequestDTO withdrawOrder){
		Order order = service.withdraw(withdrawOrder);
		return ResponseEntity.ok().body(order);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable long id, @RequestBody Account acc) {
		acc = service.updateAccount(id, acc);
		return ResponseEntity.ok().body(acc);
	}

}
