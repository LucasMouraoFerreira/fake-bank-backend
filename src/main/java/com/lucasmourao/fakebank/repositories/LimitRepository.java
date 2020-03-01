package com.lucasmourao.fakebank.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.fakebank.entities.Limit;

public interface LimitRepository extends JpaRepository<Limit, Long> {

	Page<Limit> findAll(Pageable pageable);
	
	@Query("SELECT l FROM Limit l where l.accountType = :accountType and l.orderType = :orderType")
	List<Limit> findLimit(@Param("accountType") Integer accountType, @Param("orderType") Integer orderType);
}
