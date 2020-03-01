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

import com.lucasmourao.fakebank.entities.Limit;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.services.LimitService;

@RestController
@RequestMapping(value = "/limits")
public class LimitResource {

	@Autowired
	private LimitService service;

	@GetMapping
	public ResponseEntity<Page<Limit>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Limit> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Limit> findById(@PathVariable long id) {
		Limit limit = service.findById(id);
		return ResponseEntity.ok().body(limit);
	}

	@GetMapping(value = "/findlimit")
	public ResponseEntity<Limit> findLimit(@RequestParam(value = "accountType") AccountType accountType,
			@RequestParam(value = "orderType") OrderType orderType) {
		Limit limit = service.findLimit(accountType.getCode(), orderType.getCode());				
		return ResponseEntity.ok().body(limit);
	}
	
	@PostMapping
	public ResponseEntity<Limit> insertLimit(@RequestBody Limit limit) {
		limit = service.insert(limit);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(limit.getId())
				.toUri();
		return ResponseEntity.created(location).body(limit);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteLimit(@PathVariable long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Limit> updateLimit(@PathVariable long id, @RequestBody Limit limit) {
		limit = service.updateLimit(id, limit);
		return ResponseEntity.ok().body(limit);
	}

}
