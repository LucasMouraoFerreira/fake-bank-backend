package com.lucasmourao.fakebank.repositories;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o where o.account = :account and o.orderType = :orderType and o.moment <= :finalDate and o.moment >= :initialDate")
	Page<Order> fullSearch(@Param("account") Account account, @Param("orderType") Integer orderType,
			@Param("initialDate") Instant initialDate, @Param("finalDate") Instant finalDate,
			Pageable pageable);
	
	@Query("SELECT o FROM Order o where o.account = :account and o.moment <= :finalDate and o.moment >= :initialDate")
	Page<Order> fullSearch(@Param("account") Account account,@Param("initialDate") Instant initialDate, @Param("finalDate") Instant finalDate,
			Pageable pageable);
}
