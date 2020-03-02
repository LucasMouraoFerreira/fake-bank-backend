package com.lucasmourao.fakebank.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.dto.AccountCreationDTO;
import com.lucasmourao.fakebank.dto.DepositOrderDTO;
import com.lucasmourao.fakebank.dto.SimpleAccountDTO;
import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.repositories.AccountRepository;
import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
import com.lucasmourao.fakebank.services.exceptions.FieldRequiredException;
import com.lucasmourao.fakebank.services.exceptions.InvalidFormatException;
import com.lucasmourao.fakebank.services.exceptions.NegativeValueException;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private LimitService limitService;
	
	@Autowired 
	private OrderService orderService;

	public Page<SimpleAccountDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new SimpleAccountDTO(x));
	}

	public Account findById(long id) {
		Optional<Account> acc = repository.findById(id);
		return acc.orElseThrow(() -> new ObjectNotFoundException(id));
	}

	public Page<SimpleAccountDTO> findByAgency(Integer agency, Pageable pageable) {
		return repository.findByAgency(agency, pageable).map(x -> new SimpleAccountDTO(x));
	}

	public Page<SimpleAccountDTO> fullSearch(String ownerName, String ownerCpf, Integer accountNumber,
			Integer accountDigit, Integer agency, Pageable pageable) {
		return repository.fullSearch(ownerName, ownerCpf, accountNumber, accountDigit, agency, pageable)
				.map(x -> new SimpleAccountDTO(x));
	}

	public Account insertAccount(AccountCreationDTO acc) {

		verifyAccountData(acc);
		int[] accountData = generateAccount();

		Double transferLimit = limitService.findLimit(acc.getAccountType(), OrderType.TRANSFER.getCode()).getAmount();
		Double loanLimitTotal = limitService.findLimit(acc.getAccountType(), OrderType.LOAN.getCode()).getAmount();
		Double withdrawLimit = limitService.findLimit(acc.getAccountType(), OrderType.WITHDRAW.getCode()).getAmount();

		Account accAux = new Account(null, accountData[0], 1000, acc.getPassword(), acc.getOwnerName(),
				acc.getOwnerCpf(), acc.getOwnerAddress(), 0.0, true, AccountType.valueOf(acc.getAccountType()),
				accountData[1], transferLimit, loanLimitTotal, withdrawLimit);

		return repository.save(accAux);
	}

	public Order deposit(DepositOrderDTO depositOrder) {
		verifyDepositOrder(depositOrder);
		List<Account> account = repository.findAccount(depositOrder.getAccountNumber(), depositOrder.getAccountDigit(),
				depositOrder.getAgency(), depositOrder.getOwnerName(), depositOrder.getPassword());
		if(account.isEmpty()) {
			throw new ObjectNotFoundException(-1L);
		}
		Account acc = account.get(0);
		acc.deposit(depositOrder.getAmount());
		Order order = new Order(null, Instant.now(), OrderType.DEPOSIT, depositOrder.getAmount(), 0.0, acc);
		repository.save(acc);
		return orderService.insert(order);
	}

	public void deleteById(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Account updateAccount(long id, Account acc) {
		Optional<Account> accAux = repository.findById(id);
		return updateData(accAux.orElseThrow(() -> new ObjectNotFoundException(id)), acc);
	}

	private Account updateData(Account accAux, Account acc) {

		if (acc.getOwnerAddress() != null) {
			accAux.setOwnerAddress(acc.getOwnerAddress());
		}
		if (acc.getOwnerCpf() != null) {
			accAux.setOwnerCpf(acc.getOwnerCpf());
		}
		if (acc.getOwnerName() != null) {
			accAux.setOwnerName(acc.getOwnerName());
		}
		if (acc.getPassword() != null) {
			accAux.setPassword(acc.getPassword());
		}
		if (acc.getAccountActive() != null) {
			accAux.setAccountActive(acc.getAccountActive());
		}

		return repository.save(accAux);
	}

	private int[] generateAccount() {
		Random random = new Random();
		int digit = 0;
		int account = 0;
		do {
			digit = random.ints(0, 10).findFirst().getAsInt();
			account = random.ints(0, 100000).findFirst().getAsInt();
		} while (!repository.findAccount(account, digit, 1000).isEmpty());
		int[] accountData = { account, digit };
		return accountData;
	}

	private void verifyDepositOrder(DepositOrderDTO depositOrder) {
		if (depositOrder.getAccountDigit() == null) {
			throw new FieldRequiredException("Account Digit");
		}
		if (depositOrder.getAccountNumber() == null) {
			throw new FieldRequiredException("Account Number");
		}
		if (depositOrder.getAgency() == null) {
			throw new FieldRequiredException("Agency");
		}
		if (depositOrder.getAmount() == null) {
			throw new FieldRequiredException("Amount");
		} else {
			if (depositOrder.getAmount() < 0) {
				throw new NegativeValueException("Amount");
			}
		}
		if (depositOrder.getOwnerName() == null) {
			throw new FieldRequiredException("Name");
		}
		if (depositOrder.getPassword() == null) {
			throw new FieldRequiredException("Password");
		}
	}

	private void verifyAccountData(AccountCreationDTO acc) {
		if (acc.getAccountType() == null) {
			throw new FieldRequiredException("Account Type");
		}
		if (acc.getPassword() == null) {
			throw new FieldRequiredException("Password");
		}
		if (acc.getOwnerName() == null) {
			throw new FieldRequiredException("Name");
		}
		if (acc.getOwnerCpf() == null) {
			throw new FieldRequiredException("CPF");
		}
		if (acc.getOwnerAddress() == null) {
			throw new FieldRequiredException("Address");
		}
		if (!acc.getPassword().matches("[0-9]+") || acc.getPassword().length() != 6) {
			throw new InvalidFormatException("Password must contain only 6 digits.");
		}
		if (!acc.getOwnerCpf().matches("[0-9]+") || acc.getOwnerCpf().length() != 11) {
			throw new InvalidFormatException("CPF must contain only 11 digits.");
		}
		if (!acc.getOwnerName().matches("[a-zA-Z\\s]+")) {
			throw new InvalidFormatException("Name must contain only letters.");
		}
	}
}
