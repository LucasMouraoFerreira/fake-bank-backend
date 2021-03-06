package com.lucasmourao.fakebank.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.entities.Limit;
import com.lucasmourao.fakebank.entities.LoanOrder;
import com.lucasmourao.fakebank.entities.LoanRate;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.entities.TransferOrder;
import com.lucasmourao.fakebank.entities.User;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.repositories.AccountRepository;
import com.lucasmourao.fakebank.repositories.FeeRepository;
import com.lucasmourao.fakebank.repositories.LimitRepository;
import com.lucasmourao.fakebank.repositories.LoanRateRepository;
import com.lucasmourao.fakebank.repositories.OrderRepository;
import com.lucasmourao.fakebank.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private FeeRepository feeRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private LimitRepository limitRepository;
	
	@Autowired
	private LoanRateRepository loanRateRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... agrs) throws Exception {

		Account acc1 = new Account(null, 10125, 1000, 0.0, AccountType.STANDARD, 3, 2500.0, 10000.0, 1000.0);
		Account acc2 = new Account(null, 10124, 1000, 0.0, AccountType.PREMIUM, 5, 5000.0, 50000.0, 2500.0);
		accountRepository.saveAll(Arrays.asList(acc1, acc2));

		User user1 = new User(null,"Lucas Ferreira","Rua x 19","12345678905","1000101253","123456", true, true, true, true, acc1);
		User user2 = new User(null,"Danilo Ferreira","Rua x 17","12545676905","1000101245","123456", true, true, true, true, acc2);
		userRepository.saveAll(Arrays.asList(user1,user2));
		
		
		Fee f2 = new Fee(null, "Transfer Fee - Premium", "Transfer fee for a Premium account", 0.003, 8.0,
				AccountType.PREMIUM, OrderType.TRANSFER);
		Fee f3 = new Fee(null, "Withdraw Fee - Premium", "Withdraw fee for a Premium account", 0.0, 5.0,
				AccountType.PREMIUM, OrderType.WITHDRAW);
		Fee f4 = new Fee(null, "", "", 0.0, 120.0, AccountType.PREMIUM, OrderType.CARD_ANNUITY);
		Fee f5 = new Fee(null, "", "", 0.0, 36.0, AccountType.STANDARD, OrderType.CARD_ANNUITY);
		Fee f6 = new Fee(null, "", "", 0.0, 8.0, AccountType.PREMIUM, OrderType.MONTHLY_FEE);
		Fee f7 = new Fee(null, "", "", 0.0, 5.0, AccountType.STANDARD, OrderType.MONTHLY_FEE);
		feeRepository.saveAll(Arrays.asList(f2, f3, f4, f5, f6, f7));

		Order od1 = new Order(null, Instant.parse("2020-02-02T00:00:00Z"), OrderType.DEPOSIT, 500.00, 0.0, acc1);
		Order od2 = new Order(null, Instant.now(), OrderType.WITHDRAW, 300.00, 5.0, acc2);
		Order od3 = new TransferOrder(null, Instant.now(), 500.00, 7.00, acc1, acc2.getId());
		Order od4 = new LoanOrder(null, Instant.now(), 600.00, 0.0, acc2, 0.5, 3);
		Order od5 = new LoanOrder(null, Instant.parse("2020-02-02T00:00:00Z"), 600.00, 0.0, acc2, 0.5, 3);
		orderRepository.saveAll(Arrays.asList(od1, od2, od3, od4, od5));

		Limit l1 = new Limit(null, OrderType.LOAN, AccountType.PREMIUM, 50000.00);
		Limit l2 = new Limit(null, OrderType.WITHDRAW, AccountType.PREMIUM, 2500.00);
		Limit l3 = new Limit(null, OrderType.TRANSFER, AccountType.PREMIUM, 5000.00);
		limitRepository.saveAll(Arrays.asList(l1, l2, l3));
		
		LoanRate lr1 = new LoanRate(null,AccountType.PREMIUM,0.10);
		LoanRate lr2 = new LoanRate(null,AccountType.STANDARD,0.15);
		LoanRate lr3 = new LoanRate(null,AccountType.STUDENT,0.20);
		loanRateRepository.saveAll(Arrays.asList(lr1,lr2,lr3));

	}
}
