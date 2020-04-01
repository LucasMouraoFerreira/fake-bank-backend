package com.lucasmourao.fakebank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.lucasmourao.fakebank.entities.User;
import com.lucasmourao.fakebank.repositories.UserRepository;
import com.lucasmourao.fakebank.services.exceptions.UsernameNotFoundException;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = repository.findByUserName(username);
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Account " + username + " not found");
		}	
	}	
	
}
