package br.com.fta.detector.model;

import br.com.fta.model.BankAccount;
import br.com.fta.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudDetectorTest {

	private final FraudDetector detector = new FraudDetector();
	private final List<Transaction> transactions = new ArrayList<>();
	
	private static final LocalDateTime DATE = LocalDateTime.of(2022, 1, 1, 15, 30);
	private static final BankAccount ACCOUNT_1 = new BankAccount("Foo", "0001", "00001-1");
	private static final BankAccount ACCOUNT_2 = new BankAccount("Bar", "0002", "00002-1");
	private static final BankAccount ACCOUNT_3 = new BankAccount("FooBar", "0003", "00003-1");
	
	private static final String LIMIT_TRANSACTION = "100000";
	
	private static final Transaction VALID_TRANSACTION =
			new Transaction(ACCOUNT_1, ACCOUNT_2, "50000", DATE);
	
	private static final String MAP_ENTRY = "Entry";
	private static final String MAP_EXIT = "Exit";
	
	@Test
	void givenListOfValidTransactions_whenAnalyze_thenReturnEmptyList() {
		transactions.add(VALID_TRANSACTION);
		
		List<Transaction> list = detector.analyzeTransactions(transactions);
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void givenListWithSuspiciusTransaction_whenAnalyze_thenReturnListWithFraudTransaction() {
		Transaction suspiciousTransaction = new Transaction(ACCOUNT_1, ACCOUNT_2, LIMIT_TRANSACTION, DATE);
		
		transactions.addAll(List.of(
				VALID_TRANSACTION, 
				new Transaction(ACCOUNT_1, ACCOUNT_2, LIMIT_TRANSACTION, DATE),
				VALID_TRANSACTION
				));
		
		List<Transaction> list = detector.analyzeTransactions(transactions);
		
		assertEquals(1, list.size());
		assertEquals(suspiciousTransaction, list.get(0));
	}
	
	@Test
	void givenListOfValidBankAccounts_whenAnalyze_thenReturnEmptyList() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "100000", DATE),
				new Transaction(ACCOUNT_2, ACCOUNT_3, "500000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "500000", DATE)
				));

		List<FraudAccount> list = detector.analyzeBankAccounts(transactions).stream().toList();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void givenBankAccountTransferringSuspiciousAmount_whenAnalyze_thenReturnListWithFraudBankAccount() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "500000", DATE),
				new Transaction(ACCOUNT_1, ACCOUNT_3, "500001", DATE)
				));

		List<FraudAccount> list = detector.analyzeBankAccounts(transactions).stream().toList();

		assertEquals(1, list.size());
		assertEquals(list.get(0).type(), MAP_EXIT);
		assertEquals(list.get(0).value(), new BigDecimal("1000001"));
	}
	
	@Test
	void givenBankAccountReceivingSuspiciousAmount_whenAnalyze_thenReturnListWithFraudBankAccount() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_2, ACCOUNT_1, "500000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "500001", DATE)
				));

		List<FraudAccount> list = detector.analyzeBankAccounts(transactions).stream().toList();

		assertEquals(1, list.size());
		assertEquals(list.get(0).type(), MAP_ENTRY);
		assertEquals(list.get(0).value(), new BigDecimal("1000001"));
		
	}
	
	@Test
	void givenListOfValidAgencies_whenAnalyze_thenReturnEmptyList() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "10000000", DATE),
				new Transaction(ACCOUNT_2, ACCOUNT_3, "50000000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "50000000", DATE)
				));

		List<FraudAgency> list = detector.analyzeAgency(transactions).stream().toList();

		assertTrue(list.isEmpty());
	}

	@Test
	void givenAgencyTransferringSuspiciousAmount_whenAnalyze_thenReturnListWithFraudAgency() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "500000000", DATE),
				new Transaction(ACCOUNT_1, ACCOUNT_3, "500000001", DATE)
				));

		List<FraudAgency> list = detector.analyzeAgency(transactions).stream().toList();

		assertEquals(1, list.size());
		assertEquals(list.get(0).type(), MAP_EXIT);
		assertEquals(list.get(0).value(), new BigDecimal("1000000001"));
	}
	
	@Test
	void givenAgencyReceivingSuspiciousAmount_whenAnalyze_thenReturnListWithFraudAgency() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_2, ACCOUNT_1, "500000000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "500000001", DATE)
				));

		List<FraudAgency> list = detector.analyzeAgency(transactions).stream().toList();

		assertEquals(1, list.size());
		assertEquals(list.get(0).type(), MAP_ENTRY);
		assertEquals(list.get(0).value(), new BigDecimal("1000000001"));
		
	}
}
