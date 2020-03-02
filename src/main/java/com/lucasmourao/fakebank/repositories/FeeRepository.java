package com.lucasmourao.fakebank.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.fakebank.entities.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {

	Page<Fee> findAll(Pageable pageable);
	
	@Query("SELECT f FROM Fee f where UPPER(f.name) like UPPER(concat('%', :name,'%'))")
	Page<Fee> findByName(@Param("name") String name, Pageable pageable);
	
	@Query("SELECT f FROM Fee f where UPPER(f.name) like UPPER(concat('%', :name,'%')) and f.accountType = :accountType")
	Page<Fee> findByNameAndAccountType(@Param("name") String name,@Param("accountType") Integer accountType, Pageable pageable);
	
	@Query("SELECT f FROM Fee f where UPPER(f.name) like UPPER(concat('%', :name,'%')) and f.orderType = :orderType")
	Page<Fee> findByNameAndOrderType(@Param("name") String name,@Param("orderType") Integer orderType, Pageable pageable);
	
	@Query("SELECT f FROM Fee f where UPPER(f.name) like UPPER(concat('%', :name,'%')) and f.orderType = :orderType and f.accountType = :accountType")
	Page<Fee> fullSearch(@Param("name") String name,@Param("orderType") Integer orderType ,@Param("accountType") Integer accountType, Pageable pageable);

	@Query("SELECT f FROM Fee f where f.orderType = :orderType and f.accountType = :accountType")
	List<Fee> findFee(@Param("accountType") Integer accountType ,@Param("orderType") Integer orderType);
}
