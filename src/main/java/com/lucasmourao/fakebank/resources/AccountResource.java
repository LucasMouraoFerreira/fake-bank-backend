package com.lucasmourao.fakebank.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
}
