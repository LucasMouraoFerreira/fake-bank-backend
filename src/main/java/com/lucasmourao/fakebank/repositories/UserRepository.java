package com.lucasmourao.fakebank.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.fakebank.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u where (u.userName = :userName) or (UPPER(u.fullName) like UPPER(concat('%', :fullName,'%'))) or (u.cpf = :cpf)")
	Page<User> fullSearch(@Param("userName") String userName, @Param("fullName") String fullName,
			@Param("cpf") String cpf, Pageable pageable);
	
	@Query("SELECT u FROM User u where u.userName = :userName")
	User findByUserName(@Param("userName") String userName);
}
