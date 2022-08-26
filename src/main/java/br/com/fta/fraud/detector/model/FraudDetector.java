package br.com.fta.fraud.detector.model;

import br.com.fta.transaction.domain.BankAccount;
import br.com.fta.transaction.domain.Transaction;

import java.math.BigDecimal;
import java.util.*;

public class FraudDetector {
	
	private static final BigDecimal LIMIT_VALUE_TRANSACTION = new BigDecimal("100000"); // 100.000
	private static final BigDecimal LIMIT_VALUE_ACCOUNT = new BigDecimal("1000000"); // 1.000.000
	private static final BigDecimal LIMIT_VALUE_AGENCY = new BigDecimal("1000000000"); // 1.000.000.000
	
	private static final String MAP_ENTRY = "Entry";
	private static final String MAP_EXIT = "Exit";

	public List<Transaction> analyzeTransactions(List<Transaction> transactions) {
		List<Transaction> fraudTransactions = new ArrayList<>();
		
		transactions.forEach(transaction -> {
			BigDecimal value = new BigDecimal(transaction.getValue());
			if (isValueGreaterOrEqual(value, LIMIT_VALUE_TRANSACTION)) {
				fraudTransactions.add(transaction);
			}
		});
		return fraudTransactions;
	}


	public Set<FraudAccount> analyzeBankAccounts(List<Transaction> transactions) {
		SeparateMaps<BankAccount> separateMaps = new SeparateMaps<>();

		transactions.forEach(transaction -> {
			BigDecimal value = new BigDecimal(transaction.getValue());
			BankAccount origin = transaction.getOrigin();
			BankAccount destination = transaction.getDestination();

			separateMaps.byTransferAndDeposits(value, origin, destination);
		});

		Set<FraudAccount> set = new HashSet<>();
		Set<FraudAccount> setEntry = checkFraudsAccount(set, separateMaps.getDeposits(), MAP_ENTRY);
		return checkFraudsAccount(setEntry, separateMaps.getTransfers(), MAP_EXIT);
	}

	public Set<FraudAgency> analyzeAgency(List<Transaction> transactions) {
		SeparateMaps<BankAgency> separateMaps = new SeparateMaps<>();

		transactions.forEach(transaction -> {
			BigDecimal value = new BigDecimal(transaction.getValue());
			BankAccount accountOrigin = transaction.getOrigin();
			BankAccount accountDestination = transaction.getDestination();
			BankAgency origin = new BankAgency(accountOrigin);
			BankAgency destination = new BankAgency(accountDestination);

			separateMaps.byTransferAndDeposits(value, origin, destination);
		});

		Set<FraudAgency> set = new HashSet<>();
		Set<FraudAgency> setEntry = checkFraudsAgency(set, separateMaps.getDeposits(), MAP_ENTRY);
		return checkFraudsAgency(setEntry, separateMaps.getTransfers(), MAP_EXIT);
	}

	private Set<FraudAgency> checkFraudsAgency(Set<FraudAgency> set, Map<BankAgency, BigDecimal> map, String type) {
		map.forEach((k, v) -> {
			if (isValueGreater(v, LIMIT_VALUE_AGENCY)) {
				set.add(new FraudAgency(type, k, v));
			}
		});
		return set;
	}

	private Set<FraudAccount> checkFraudsAccount(Set<FraudAccount> set, Map<BankAccount, BigDecimal> map, String type) {
		map.forEach((k, v) -> {
			if (isValueGreater(v, LIMIT_VALUE_ACCOUNT)) {
				set.add(new FraudAccount(type, k, v));
			}
		});
		return set;
	}
	
	private boolean isValueGreaterOrEqual(BigDecimal value, BigDecimal limit) {
		return limit.compareTo(value) <= 0;
	}

	private boolean isValueGreater(BigDecimal value, BigDecimal limit) {
		return limit.compareTo(value) < 0;
	}
}

