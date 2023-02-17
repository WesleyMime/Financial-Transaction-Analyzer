package br.com.fta.detector.model;

import br.com.fta.model.BankAccount;
import br.com.fta.model.BankAgency;
import br.com.fta.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FraudDetector {
	
	private static final BigDecimal LIMIT_VALUE_TRANSACTION = new BigDecimal("100000"); // 100.000
	private static final BigDecimal LIMIT_VALUE_ACCOUNT = new BigDecimal("1000000"); // 1.000.000
	private static final BigDecimal LIMIT_VALUE_AGENCY = new BigDecimal("1000000000"); // 1.000.000.000
	
	private static final String MAP_ENTRY = "Entry";
	private static final String MAP_EXIT = "Exit";

	public Frauds analyzeTransactions(List<Transaction> transactions) {
		Set<Transaction> fraudTransactions = new HashSet<>();
		DepositAndTransferValues<BankAccount> bankAccountDepositAndTransferValues = new DepositAndTransferValues<>();
		DepositAndTransferValues<BankAgency> bankAgencyDepositAndTransferValues = new DepositAndTransferValues<>();

		transactions.forEach(transaction -> {
			analyzeTransactionsValues(fraudTransactions, transaction);
			groupTransactionWithBankAccount(bankAccountDepositAndTransferValues, transaction);
			groupTransactionWithBankAgency(bankAgencyDepositAndTransferValues, transaction);
		});
		Set<FraudAccount> fraudAccounts = analyzeAccounts(bankAccountDepositAndTransferValues);
		Set<FraudAgency> fraudAgencies = analyzeAgencies(bankAgencyDepositAndTransferValues);

		return new Frauds(fraudTransactions, fraudAccounts, fraudAgencies);
	}

	private void analyzeTransactionsValues(Set<Transaction> fraudTransactions, Transaction transaction) {
		BigDecimal value = new BigDecimal(transaction.value());
		if (isValueGreaterOrEqual(value, LIMIT_VALUE_TRANSACTION)) {
			fraudTransactions.add(transaction);
		}
	}

	private void groupTransactionWithBankAccount(DepositAndTransferValues<BankAccount> depositAndTransferValues,
												 Transaction transaction) {
		BigDecimal value = new BigDecimal(transaction.value());
		BankAccount origin = transaction.origin();
		BankAccount destination = transaction.destination();

		depositAndTransferValues.add(value, origin, destination);
	}

	private Set<FraudAccount> analyzeAccounts(DepositAndTransferValues<BankAccount> depositAndTransferValues) {
		Set<FraudAccount> set = new HashSet<>();
		checkFraudsAccount(set, depositAndTransferValues.getDeposits(), MAP_ENTRY);
		checkFraudsAccount(set, depositAndTransferValues.getTransfers(), MAP_EXIT);
		return set;
	}

	private void checkFraudsAccount(Set<FraudAccount> set, Map<BankAccount, BigDecimal> map, String type) {
		map.forEach((k, v) -> {
			if (isValueGreater(v, LIMIT_VALUE_ACCOUNT)) {
				set.add(new FraudAccount(type, k, v));
			}
		});
	}

	private void groupTransactionWithBankAgency(DepositAndTransferValues<BankAgency> depositAndTransferValues,
												Transaction transaction) {
		BigDecimal value = new BigDecimal(transaction.value());
		BankAccount accountOrigin = transaction.origin();
		BankAccount accountDestination = transaction.destination();
		BankAgency origin = new BankAgency(accountOrigin.bank(), accountOrigin.agency());
		BankAgency destination = new BankAgency(accountDestination.bank(), accountDestination.agency());

		depositAndTransferValues.add(value, origin, destination);
	}

	private Set<FraudAgency> analyzeAgencies(DepositAndTransferValues<BankAgency> depositAndTransferValues) {
		Set<FraudAgency> fraudAgencySet = new HashSet<>();
		checkFraudsAgency(fraudAgencySet, depositAndTransferValues.getDeposits(), MAP_ENTRY);
		checkFraudsAgency(fraudAgencySet, depositAndTransferValues.getTransfers(), MAP_EXIT);
		return fraudAgencySet;
	}

	private void checkFraudsAgency(Set<FraudAgency> set, Map<BankAgency, BigDecimal> map, String type) {
		map.forEach((k, v) -> {
			if (isValueGreater(v, LIMIT_VALUE_AGENCY)) {
				set.add(new FraudAgency(type, k, v));
			}
		});
	}
	
	private boolean isValueGreaterOrEqual(BigDecimal value, BigDecimal limit) {
		return limit.compareTo(value) <= 0;
	}

	private boolean isValueGreater(BigDecimal value, BigDecimal limit) {
		return limit.compareTo(value) < 0;
	}
}

