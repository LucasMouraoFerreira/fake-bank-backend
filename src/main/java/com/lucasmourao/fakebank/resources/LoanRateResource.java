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

import com.lucasmourao.fakebank.entities.LoanRate;
import com.lucasmourao.fakebank.services.LoanRateService;

@RestController
@RequestMapping(value = "/loanrates")
public class LoanRateResource {

	@Autowired
	private LoanRateService service;

	@GetMapping
	public ResponseEntity<Page<LoanRate>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<LoanRate> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<LoanRate> findById(@PathVariable long id) {
		LoanRate loanRate = service.findById(id);
		return ResponseEntity.ok().body(loanRate);
	}

	@PostMapping
	public ResponseEntity<LoanRate> insertLoanRate(@RequestBody LoanRate loanRate) {
		loanRate = service.insert(loanRate);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loanRate.getId())
				.toUri();
		return ResponseEntity.created(location).body(loanRate);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteLoanRate(@PathVariable long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<LoanRate> updateLoanRate(@PathVariable long id, @RequestBody LoanRate loanRate) {
		loanRate = service.updateLoanRate(id, loanRate);
		return ResponseEntity.ok().body(loanRate);
	}

}
