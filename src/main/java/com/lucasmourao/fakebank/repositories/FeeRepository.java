package com.lucasmourao.fakebank.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmourao.fakebank.entities.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {

	Page<Fee> findAll(Pageable pageable);
}
