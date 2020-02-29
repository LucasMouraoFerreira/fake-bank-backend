package com.lucasmourao.fakebank.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.entities.LoanOrder;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.entities.TransferOrder;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.repositories.AccountRepository;
import com.lucasmourao.fakebank.repositories.FeeRepository;
import com.lucasmourao.fakebank.repositories.OrderRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private FeeRepository feeRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public void run(String... agrs) throws Exception {
		
		Account acc1 = new Account(null,10125,1001,123456,"Lucas Ferreira","12590136489","Rua exemplo 54",0.0,true,AccountType.STANDARD);
		Account acc2 = new Account(null,10124,1000,654321,"Danilo Ferreira","12594536479","Rua exemplo 33",0.0,true,AccountType.PREMIUM);
		accountRepository.saveAll(Arrays.asList(acc1, acc2));
		
		Fee f1 = new Fee(null, "Transfer Fee - Standard", "Transfer fee for a standard account",0.0,7.0,AccountType.STANDARD,OrderType.TRANSFER);
		Fee f2 = new Fee(null, "Transfer Fee - Premium", "Transfer fee for a Premium account",0.0,8.0,AccountType.PREMIUM,OrderType.TRANSFER);
		feeRepository.saveAll(Arrays.asList(f1,f2));
		
		Order od1 = new Order(null, Instant.parse("2020-02-02T00:00:00Z"), OrderType.DEPOSIT, 500.00, 0.0, acc1);
		Order od2 = new Order(null, Instant.now(), OrderType.WITHDRAW, 300.00, 5.0, acc2);
		Order od3 = new TransferOrder(null, Instant.now(),500.00,7.00,acc1,acc2.getId());
		Order od4 = new LoanOrder(null,Instant.now(),600.00, 0.0, acc2, 0.5, 3);
		orderRepository.saveAll(Arrays.asList(od1,od2,od3,od4));
	}
}
