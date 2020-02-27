package com.lucasmourao.fakebank.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.repositories.AccountRepository;
import com.lucasmourao.fakebank.repositories.FeeRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private FeeRepository feeRepository;

	@Override
	public void run(String... agrs) throws Exception {
		
		Account acc1 = new Account(null,10125,1001,123456,"Lucas Ferreira","12590136489","Rua exemplo 54",0.0,true,AccountType.STANDARD);
		Account acc2 = new Account(null,10124,1000,654321,"Danilo Ferreira","12594536479","Rua exemplo 33",0.0,true,AccountType.PREMIUM);
		accountRepository.saveAll(Arrays.asList(acc1, acc2));
		
		Fee f1 = new Fee(null, "Transfer Fee - Standard", "Transfer fee for a standard account",0.0,7.0,AccountType.STANDARD,OrderType.TRANSFER);
		Fee f2 = new Fee(null, "Transfer Fee - Premium", "Transfer fee for a Premium account",0.0,8.0,AccountType.PREMIUM,OrderType.TRANSFER);
		feeRepository.saveAll(Arrays.asList(f1,f2));
		
	}
}
