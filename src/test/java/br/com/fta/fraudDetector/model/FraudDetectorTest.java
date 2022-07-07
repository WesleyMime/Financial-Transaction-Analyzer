package br.com.fta.fraudDetector.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.fta.transaction.domain.BankAccount;
import br.com.fta.transaction.domain.Transaction;

class FraudDetectorTest {

	private FraudDetector detector = new FraudDetector();
	private List<Transaction> transactions = new ArrayList<>();
	
	private static final LocalDateTime DATE = LocalDateTime.of(2022, 01, 01, 15, 30);
	private static final BankAccount ACCOUNT_1 = new BankAccount("Foo", "0001", "00001-1");
	private static final BankAccount ACCOUNT_2 = new BankAccount("Bar", "0002", "00002-1");
	private static final BankAccount ACCOUNT_3 = new BankAccount("FooBar", "0003", "00003-1");
	
	private static final String LIMIT_TRANSACTION = "100000";
	
	private static final Transaction VALID_TRANSACTION = 
			new Transaction(ACCOUNT_1, ACCOUNT_2, "50000", DATE);
	
	private static final String MAP_ENTRY = "entry";
	private static final String MAP_EXIT = "exit";
	
	@Test
	void givenListOfValidTransactions_whenAnalyze_thenReturnEmptyList() {
		transactions.addAll(List.of(VALID_TRANSACTION));
		
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
	void givenListOfValidBankAccounts_whenAnalyze_thenReturnEmptyMap() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "100000", DATE),
				new Transaction(ACCOUNT_2, ACCOUNT_3, "500000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "500000", DATE)
				));
		
		Map<String, Map<BankAccount, BigDecimal>> entryExit = detector.analyzeBankAccounts(transactions);
		
		assertTrue(entryExit.get(MAP_ENTRY).isEmpty());
		assertTrue(entryExit.get(MAP_EXIT).isEmpty());
	}
	
	@Test
	void givenBankAccountTransferingSuspiciousAmount_whenAnalyze_thenReturnMapWithFraudBankAccount() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "500000", DATE),
				new Transaction(ACCOUNT_1, ACCOUNT_3, "500001", DATE)
				));
		
		Map<String, Map<BankAccount, BigDecimal>> entryExit = detector.analyzeBankAccounts(transactions);
		
		Map<BankAccount, BigDecimal> entry = entryExit.get(MAP_ENTRY);
		Map<BankAccount, BigDecimal> exit = entryExit.get(MAP_EXIT);
		
		assertEquals(0, entry.size());
		assertEquals(1, exit.size());
		assertTrue(exit.get(ACCOUNT_1).equals(new BigDecimal("1000001")));
	}
	
	@Test
	void givenBankAccountReceivingSuspiciousAmount_whenAnalyze_thenReturnMapWithFraudBankAccount() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_2, ACCOUNT_1, "500000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "500001", DATE)
				));
		
		Map<String, Map<BankAccount, BigDecimal>> entryExit = detector.analyzeBankAccounts(transactions);
		
		Map<BankAccount, BigDecimal> entry = entryExit.get(MAP_ENTRY);
		Map<BankAccount, BigDecimal> exit = entryExit.get(MAP_EXIT);
		
		assertEquals(1, entry.size());
		assertEquals(0, exit.size());
		assertTrue(entry.get(ACCOUNT_1).equals(new BigDecimal("1000001")));
		
	}
	
	@Test
	void givenListOfValidAgencies_whenAnalyze_thenReturnEmptyMap() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "10000000", DATE),
				new Transaction(ACCOUNT_2, ACCOUNT_3, "50000000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "50000000", DATE)
				));
		
		Map<String, Map<BankAgency, BigDecimal>> entryExit = detector.analyzeAgency(transactions);
		
		assertTrue(entryExit.get(MAP_ENTRY).isEmpty());
		assertTrue(entryExit.get(MAP_EXIT).isEmpty());
	}

	@Test
	void givenAgencyTransferingSuspiciousAmount_whenAnalyze_thenReturnMapWithFraudAgency() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_1, ACCOUNT_2, "500000000", DATE),
				new Transaction(ACCOUNT_1, ACCOUNT_3, "500000001", DATE)
				));
		
		Map<String, Map<BankAgency, BigDecimal>> entryExit = detector.analyzeAgency(transactions);
		
		Map<BankAgency, BigDecimal> entry = entryExit.get(MAP_ENTRY);
		Map<BankAgency, BigDecimal> exit = entryExit.get(MAP_EXIT);
		
		assertEquals(0, entry.size());
		assertEquals(1, exit.size());
		assertTrue(exit.get(new BankAgency(ACCOUNT_1)).equals(new BigDecimal("1000000001")));
	}
	
	@Test
	void givenAgencyReceivingSuspiciousAmount_whenAnalyze_thenReturnMapWithFraudAgency() {
		transactions.addAll(List.of(
				new Transaction(ACCOUNT_2, ACCOUNT_1, "500000000", DATE),
				new Transaction(ACCOUNT_3, ACCOUNT_1, "500000001", DATE)
				));
		
		Map<String, Map<BankAgency, BigDecimal>> entryExit = detector.analyzeAgency(transactions);
		
		Map<BankAgency, BigDecimal> entry = entryExit.get(MAP_ENTRY);
		Map<BankAgency, BigDecimal> exit = entryExit.get(MAP_EXIT);
		
		assertEquals(1, entry.size());
		assertEquals(0, exit.size());
		assertTrue(entry.get(new BankAgency(ACCOUNT_1)).equals(new BigDecimal("1000000001")));
		
	}
}
