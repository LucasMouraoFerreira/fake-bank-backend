package com.lucasmourao.fakebank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmourao.fakebank.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
