package com.lucasmourao.fakebank.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.repositories.AccountRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void run(String... agrs) throws Exception {
		
		Account acc1 = new Account(null,10125,1000,123456,"Lucas Ferreira","12590136489","Rua exemplo 54",0.0,true);
		Account acc2 = new Account(null,10124,1000,654321,"Danilo Ferreira","12594536479","Rua exemplo 33",0.0,true);
		accountRepository.saveAll(Arrays.asList(acc1, acc2));
		
	}
}
