package br.com.fta.fraudDetector.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fta.transaction.domain.BankAccount;
import br.com.fta.transaction.domain.Transaction;

public class FraudDetector {
	
	private static final BigDecimal LIMIT_VALUE_TRANSACTION = new BigDecimal("100000"); // 100.000
	private static final BigDecimal LIMIT_VALUE_ACCOUNT = new BigDecimal("1000000"); // 1.000.000
	private static final BigDecimal LIMIT_VALUE_AGENCY = new BigDecimal("1000000000"); // 1.000.000.000
	
	private static final String MAP_ENTRY = "entry";
	private static final String MAP_EXIT = "exit";

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


	public Map<String, Map<BankAccount, BigDecimal>> analyzeBankAccounts(List<Transaction> transactions) {
		Map<String, Map<BankAccount, BigDecimal>> fraudAccounts = new HashMap<>();
		
		SeparateMaps<BankAccount> separateMaps = new SeparateMaps<>(LIMIT_VALUE_ACCOUNT);
		
		transactions.forEach(transaction -> {
			BigDecimal value = new BigDecimal(transaction.getValue());
			BankAccount origin = transaction.getOrigin();
			BankAccount destination = transaction.getDestination();
			
			separateMaps.byTransferAndDeposits(value, origin, destination);
		});
		
		fraudAccounts.put(MAP_ENTRY, separateMaps.getFraudDeposits());
		fraudAccounts.put(MAP_EXIT, separateMaps.getFraudTransfers());
		return fraudAccounts;
	}
	
	public Map<String, Map<BankAgency, BigDecimal>> analyzeAgency(List<Transaction> transactions) {
		Map<String, Map<BankAgency, BigDecimal>> fraudAgencies = new HashMap<>();
		
		SeparateMaps<BankAgency> separateMaps = new SeparateMaps<>(LIMIT_VALUE_AGENCY);
		
		transactions.forEach(transaction -> {
			BigDecimal value = new BigDecimal(transaction.getValue());
			BankAgency origin = new BankAgency(transaction.getOrigin());
			BankAgency destination = new BankAgency(transaction.getDestination());
			
			separateMaps.byTransferAndDeposits(value, origin, destination);
		});
		
		fraudAgencies.put(MAP_ENTRY, separateMaps.getFraudDeposits());
		fraudAgencies.put(MAP_EXIT, separateMaps.getFraudTransfers());
		
		return fraudAgencies;
	}
	
	private boolean isValueGreaterOrEqual(BigDecimal value, BigDecimal limit) {
		if (limit.compareTo(value) <= 0) {
			return true;
		}
		return false;
	}
}

