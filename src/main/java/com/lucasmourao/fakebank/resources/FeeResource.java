package com.lucasmourao.fakebank.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.services.FeeService;

@RestController
@RequestMapping(value="/fees")
public class FeeResource {
	
	@Autowired
	private FeeService service;
	
	@GetMapping
	public ResponseEntity<Page<Fee>> findAll(@RequestParam(value="page",defaultValue="0") int page,
			@RequestParam(value="limit",defaultValue="5") int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<Fee> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}
}
