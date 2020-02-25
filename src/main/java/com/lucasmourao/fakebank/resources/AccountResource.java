package com.lucasmourao.fakebank.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.services.AccountService;

@RestController
@RequestMapping(value ="/accounts")
public class AccountResource {

	@Autowired
	private AccountService service;
	
	@GetMapping
	public ResponseEntity<List<Account>> findAll(){
		List<Account> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Account> findById(@PathVariable long id){
		Account acc = service.findById(id);
		return ResponseEntity.ok().body(acc);
	}
	
	@PostMapping
	public ResponseEntity<Account> insertAccount(@RequestBody Account acc){
		acc = service.insertAccount(acc);		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(acc.getId()).toUri();
		return ResponseEntity.created(location).body(acc);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable long id, @RequestBody Account acc){
		acc = service.updateAccount(id, acc);
		return ResponseEntity.ok().body(acc);
	}
	
}
