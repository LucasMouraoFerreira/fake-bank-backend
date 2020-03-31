package com.lucasmourao.fakebank.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lucasmourao.fakebank.dto.AccountCreationDTO;
import com.lucasmourao.fakebank.dto.AccountLimitsDTO;
import com.lucasmourao.fakebank.dto.AccountUpdateDTO;
import com.lucasmourao.fakebank.dto.CompleteAccountDTO;
import com.lucasmourao.fakebank.dto.LoanOrderRequestDTO;
import com.lucasmourao.fakebank.dto.OrderRequestDTO;
import com.lucasmourao.fakebank.dto.SimpleAccountDTO;
import com.lucasmourao.fakebank.dto.TransferOrderRequestDTO;
import com.lucasmourao.fakebank.entities.Account;
import com.lucasmourao.fakebank.entities.Fee;
import com.lucasmourao.fakebank.entities.LoanOrder;
import com.lucasmourao.fakebank.entities.LoanRate;
import com.lucasmourao.fakebank.entities.Order;
import com.lucasmourao.fakebank.entities.TransferOrder;
import com.lucasmourao.fakebank.entities.User;
import com.lucasmourao.fakebank.entities.enums.AccountType;
import com.lucasmourao.fakebank.entities.enums.OrderType;
import com.lucasmourao.fakebank.repositories.AccountRepository;
import com.lucasmourao.fakebank.repositories.UserRepository;
import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
import com.lucasmourao.fakebank.services.exceptions.FieldRequiredException;
import com.lucasmourao.fakebank.services.exceptions.InsufficientBalanceException;
import com.lucasmourao.fakebank.services.exceptions.InvalidFormatException;
import com.lucasmourao.fakebank.services.exceptions.LimitExceededException;
import com.lucasmourao.fakebank.services.exceptions.NegativeValueException;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LimitService limitService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private FeeService feeService;

	@Autowired
	private LoanRateService loanRateService;

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
		String userName = Integer.toString(agency) + Integer.toString(accountNumber) + Integer.toString(accountDigit);
		return userRepository.fullSearch(userName, ownerName, ownerCpf, pageable).map(x -> new SimpleAccountDTO(x));
	}

	public Account findAccount(OrderRequestDTO orderRequest) {
		List<Account> account = repository.findAccount(orderRequest.getAccountNumber(), orderRequest.getAccountDigit(),
				orderRequest.getAgency());
		if (account.isEmpty()) {
			throw new ObjectNotFoundException(-1L);
		}
		return account.get(0);
	}

	public List<Account> findAccount(TransferOrderRequestDTO orderRequest) {
		List<Account> receivingAccount = repository.findAccount(orderRequest.getReceivingAccountNumber(),
				orderRequest.getReceivingAccountDigit(), orderRequest.getReceivingAccountAgency());
		if (receivingAccount.isEmpty()) {
			throw new ObjectNotFoundException(-1L);
		}
		List<Account> account = repository.findAccount(orderRequest.getAccountNumber(), orderRequest.getAccountDigit(),
				orderRequest.getAgency());
		if (account.isEmpty()) {
			throw new ObjectNotFoundException(-1L);
		}
		account.add(receivingAccount.get(0));
		if (account.size() > 2) {
			throw new DatabaseException("Database integrity error");
		}
		return account;
	}

	public User insertAccount(AccountCreationDTO acc) {

		verifyAccountData(acc);
		int[] accountData = generateAccount();

		Double transferLimit = limitService.findLimit(acc.getAccountType(), OrderType.TRANSFER.getCode()).getAmount();
		Double loanLimitTotal = limitService.findLimit(acc.getAccountType(), OrderType.LOAN.getCode()).getAmount();
		Double withdrawLimit = limitService.findLimit(acc.getAccountType(), OrderType.WITHDRAW.getCode()).getAmount();

		Account accAux = new Account(null, accountData[0], 1000, 0.0, AccountType.valueOf(acc.getAccountType()),
				accountData[1], transferLimit, loanLimitTotal, withdrawLimit);
		repository.save(accAux);
		
		String sAcc = "1000" + Integer.toString(accountData[0]) + Integer.toString(accountData[1]);
		User user = new User(null, acc.getOwnerName(), acc.getOwnerAddress(), acc.getOwnerCpf(), sAcc , acc.getPassword(), true, true, true, true, accAux);
		userRepository.save(user);
		
		return user;
	}

	public Order deposit(OrderRequestDTO depositOrder) {
		verifyOrderRequest(depositOrder);
		Account acc = findAccount(depositOrder);
		acc.deposit(depositOrder.getAmount());
		Order order = new Order(null, Instant.now(), OrderType.DEPOSIT, depositOrder.getAmount(), 0.0, acc);
		repository.save(acc);
		return orderService.insert(order);
	}

	public Order withdraw(OrderRequestDTO withdrawOrder) {
		verifyOrderRequest(withdrawOrder);
		Account acc = findAccount(withdrawOrder);
		double result[] = verifyBalanceAndLimit(acc, withdrawOrder, OrderType.WITHDRAW);
		double amount = result[0];
		double feeTotal = result[1];
		acc.withdraw(amount);
		Order order = new Order(null, Instant.now(), OrderType.WITHDRAW, withdrawOrder.getAmount(), feeTotal, acc);
		repository.save(acc);
		return orderService.insert(order);
	}

	public LoanOrder loan(LoanOrderRequestDTO loanOrder) {
		verifyLoanOrderRequest(loanOrder);
		Account acc = findAccount(loanOrder);
		LoanRate loanRate = loanRateService.findLoanRate(acc.getAccountType().getCode());
		verifyLimit(acc, loanOrder, OrderType.LOAN);
		acc.deposit(loanOrder.getAmount());
		acc.decreaseLoanLimitCurrent(loanOrder.getAmount());
		repository.save(acc);
		LoanOrder order = new LoanOrder(null, Instant.now(), loanOrder.getAmount(), 0.0, acc, loanRate.getRate(),
				loanOrder.getNumberOfInstallments());
		return orderService.insert(order);
	}

	public TransferOrder transfer(TransferOrderRequestDTO transferOrder) {
		verifyTransferOrderRequest(transferOrder);
		List<Account> accounts = findAccount(transferOrder);
		Account receivingAcc = accounts.get(1);
		Account acc = accounts.get(0);
		double result[] = verifyBalanceAndLimit(acc, transferOrder, OrderType.TRANSFER);
		double amount = result[0];
		double feeTotal = result[1];
		acc.withdraw(amount);
		receivingAcc.deposit(transferOrder.getAmount());
		repository.saveAll(Arrays.asList(acc, receivingAcc));
		TransferOrder order = new TransferOrder(null, Instant.now(), transferOrder.getAmount(), feeTotal, acc,
				receivingAcc.getId());
		Order receivedOrder = new Order(null, Instant.now(), OrderType.TRANSFER_RECEIVED, transferOrder.getAmount(),
				0.0, receivingAcc);
		orderService.insert(receivedOrder);
		return orderService.insert(order);
	}

	@Scheduled(cron = "0 0 21 7 * ?", zone = "GMT") // Runs once a month on the 7th at 9pm - GMT //[Seconds] [Minutes]
													// [Hours] [Day of month] [Month] [Day of week] [Year]
	private void chargeAccountsMonthly() {
		List<Account> accounts = repository.findAll();
		for (Account acc : accounts) {
			accountMonthlyFee(acc);
		}
	}

	@Scheduled(cron = "0 0 21 7 * ?", zone = "GMT") // Runs once a month on the 7th at 9pm - GMT //[Seconds] [Minutes]
													// [Hours] [Day of month] [Month] [Day of week] [Year]
	private void loanMonthlyCharge() {
		List<Order> loanOrders = orderService.findByOrderType(OrderType.LOAN.getCode());
		for (Order loanOrder : loanOrders) {
			long daysSinceOrderCreation = loanOrder.getMoment().until(Instant.now(), ChronoUnit.DAYS);
			if (daysSinceOrderCreation >= 30 && ((LoanOrder) loanOrder).getPaidInstallments() < ((LoanOrder) loanOrder)
					.getNumberOfInstallments()) {
				((LoanOrder) loanOrder).incremetPaidInstallments();
				loanOrder.getAccount().withdraw(((LoanOrder) loanOrder).getAmountPerInstallment());
				loanOrder.getAccount().increaseLoanLimitCurrent(((LoanOrder) loanOrder).getAmountPerInstallment());
				orderService.saveLoanOrder((LoanOrder) loanOrder);
				repository.save(loanOrder.getAccount());
			}
		}
	}

	public void deleteById(long id) {
		try {
			Optional<Account> acc = repository.findById(id);
			userRepository.deleteById(acc.get().getUser().getId());
			for(Order order : acc.get().getOrders()) {
				orderService.delete(order.getId());
			}
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public CompleteAccountDTO updateAccount(long id, AccountUpdateDTO acc) {
		Optional<User> userAux = userRepository.findById(id);
		return new CompleteAccountDTO(updateData(userAux.orElseThrow(() -> new ObjectNotFoundException(id)), acc));
	}

	public CompleteAccountDTO updateAccountLimits(long id, AccountLimitsDTO accountLimits) {
		Account acc = findById(id);
		return new CompleteAccountDTO(UpdateLimitsData(acc, accountLimits));
	}

	private User updateData(User userAux, AccountUpdateDTO acc) {

		if (acc.getAddress() != null) {
			userAux.setAddress(acc.getAddress());
		}
		if (acc.getOwnerCpf() != null) {
			if (!acc.getOwnerCpf().matches("[0-9]+") || acc.getOwnerCpf().length() != 11) {
				throw new InvalidFormatException("CPF must contain only 11 digits.");
			}
			userAux.setCpf(acc.getOwnerCpf());
		}
		if (acc.getOwnerName() != null) {
			userAux.setFullName(acc.getOwnerName());
		}
		if (acc.getPassword() != null) {
			if (!acc.getPassword().matches("[0-9]+") || acc.getPassword().length() != 6) {
				throw new InvalidFormatException("Password must contain only 6 digits.");
			}
			userAux.setPassword(acc.getPassword());
		}
		
		return userRepository.save(userAux);
	}

	private Account UpdateLimitsData(Account acc, AccountLimitsDTO accountLimits) {
		if (accountLimits.getLoanLimitTotal() != null && accountLimits.getLoanLimitTotal() > 0.0) {
			acc.setLoanLimitTotal(accountLimits.getLoanLimitTotal());
			if (acc.getLoanLimitCurrent() > acc.getLoanLimitTotal()) {
				acc.setLoanLimitCurrent(acc.getLoanLimitTotal());
			}
		}
		if (accountLimits.getTransferLimit() != null && accountLimits.getTransferLimit() > 0.0) {
			acc.setTransferLimit(accountLimits.getTransferLimit());
		}
		if (accountLimits.getWithdrawLimit() != null && accountLimits.getWithdrawLimit() > 0.0) {
			acc.setWithdrawLimit(accountLimits.getWithdrawLimit());
		}
		return repository.save(acc);
	}

	private void accountMonthlyFee(Account acc) {
		Fee cardAnnuityFee = feeService.findFee(acc.getAccountType().getCode(), OrderType.CARD_ANNUITY.getCode());
		Fee accountMonthlyFee = feeService.findFee(acc.getAccountType().getCode(), OrderType.MONTHLY_FEE.getCode());
		Order cardAnnuityOrder = new Order(null, Instant.now(), OrderType.CARD_ANNUITY,
				cardAnnuityFee.getTotalValue() / 12, 0.0, acc);
		Order accountMonthlyOrder = new Order(null, Instant.now(), OrderType.MONTHLY_FEE,
				accountMonthlyFee.getTotalValue(), 0.0, acc);
		acc.withdraw(accountMonthlyOrder.getBaseValue() + cardAnnuityOrder.getBaseValue());
		orderService.insert(accountMonthlyOrder);
		orderService.insert(cardAnnuityOrder);
		repository.save(acc);
	}

	private int[] generateAccount() {
		Random random = new Random();
		int accountDigit = 0;
		int accountNumber = 0;
		do {
			accountDigit = random.ints(0, 10).findFirst().getAsInt();
			accountNumber = random.ints(0, 100000).findFirst().getAsInt();
		} while (!repository.findAccount(accountNumber, accountDigit, 1000).isEmpty());
		int[] accountData = { accountNumber, accountDigit };
		return accountData;
	}

	private void verifyOrderRequest(OrderRequestDTO depositOrder) {
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

	private void verifyTransferOrderRequest(TransferOrderRequestDTO transferOrder) {
		verifyOrderRequest(transferOrder);
		if (transferOrder.getReceivingAccountAgency() == null) {
			throw new FieldRequiredException("Receiving account agency");
		}
		if (transferOrder.getReceivingAccountDigit() == null) {
			throw new FieldRequiredException("Receiving account digit");
		}
		if (transferOrder.getReceivingAccountNumber() == null) {
			throw new FieldRequiredException("Receiving account number");
		}
	}

	private void verifyLoanOrderRequest(LoanOrderRequestDTO loanOrder) {
		verifyOrderRequest(loanOrder);
		if (loanOrder.getNumberOfInstallments() == null) {
			throw new FieldRequiredException("Number of installments");
		} else {
			if (loanOrder.getNumberOfInstallments() < 1) {
				throw new NegativeValueException("Number of installments");
			}
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

	private double[] verifyBalanceAndLimit(Account acc, OrderRequestDTO orderRequest, OrderType orderType) {
		Fee fee = feeService.findFee(acc.getAccountType().getCode(), orderType.getCode());
		Double withdrawPercentageFee = fee.getPercentage();
		Double withdrawTotalFee = fee.getTotalValue();
		Double amount = (orderRequest.getAmount() * (1.0 + withdrawPercentageFee)) + withdrawTotalFee;
		if (acc.getBalance() < amount) {
			throw new InsufficientBalanceException();
		}
		verifyLimit(acc, orderRequest, orderType);
		double feeTotal = (orderRequest.getAmount() * withdrawPercentageFee) + withdrawTotalFee;
		double[] result = { amount, feeTotal };
		return result;
	}

	private void verifyLimit(Account acc, OrderRequestDTO orderRequest, OrderType orderType) {
		if (orderRequest instanceof LoanOrderRequestDTO) {
			if (acc.getLoanLimitCurrent() < orderRequest.getAmount()) {
				throw new LimitExceededException(orderType.toString());
			}
		} else if (orderRequest instanceof TransferOrderRequestDTO) {
			if (acc.getTransferLimit() < orderRequest.getAmount()) {
				throw new LimitExceededException(orderType.toString());
			}
		} else {
			if (acc.getWithdrawLimit() < orderRequest.getAmount()) {
				throw new LimitExceededException(orderType.toString());
			}
		}
	}

}
