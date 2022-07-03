package br.com.fta.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.transaction.exceptions.InvalidFileException;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class TransactionServiceTest {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionService service;
	
	private static final String USERNAME = "Foo";
	
	private static final String VALID_TRANSACTION = "BYTE BANK,0001,00001-1,BANK BYTE,0001,00001-1,"
			+ "8000,2022-01-02T07:30:00\n";
	private static final String VALID_TRANSACTION_2 = "BYTE BANK,0002,00001-1,BANK BYTE,0001,00001-1,"
			+ "3000,2022-01-02T07:30:00\n";
	private static final String MISSING_VALUE_TRANSACTION = "BYTE BANK,0001,00001-1,0001,00001-1,"
			+ "8000,2022-01-02T07:30:00\n";
	private static final String BLANK_VALUE_TRANSACTION = "BYTE BANK,0001,00001-1,,0001,00001-1,"
			+ "8000,2022-01-02T07:30:00\n";
	private static final String VALID_TRANSACTION_DIFF_DATE = "BYTE BANK,0001,00001-1,BANK BYTE,0001,00001-1,"
			+ "8000,2022-02-02T07:30:00\n";

	@AfterEach
	void afterEach() {
		service.deleteTransactions();
	}
	
	@Test
	void givenValidFile_whenPostTransaction_thenInsertIntoDatabase() {
		MultipartFile file = new MockMultipartFile("file", "file", "text/csv", VALID_TRANSACTION.getBytes());

		service.postTransaction(file, USERNAME);

		List<Transaction> list = transactionRepository.findByBancoOrigem("BYTE BANK").get();
		assertEquals("BANK BYTE", list.get(0).getBancoDestino());
	}
	
	@Test
	void givenFileWithInvalidValue_whenPostTransaction_thenDiscartTransaction() {
		MultipartFile file = new MockMultipartFile("file", "file", "text/csv", BLANK_VALUE_TRANSACTION.getBytes());

		assertThrows(InvalidFileException.class, () -> service.postTransaction(file, USERNAME));
	}

	@Test
	void givenFileWithMissingInformation_whenPostTransaction_thenDiscartTransaction() {
		MultipartFile file = new MockMultipartFile("file", "file", "text/csv", MISSING_VALUE_TRANSACTION.getBytes());

		assertThrows(InvalidFileException.class, () -> service.postTransaction(file, USERNAME));
	}

	@Test
	void givenEmptyFile_whenPostTransaction_thenThrowsException() {
		MultipartFile file = new MockMultipartFile("file", "file", "text/csv", "".getBytes());
	
		assertThrows(InvalidFileException.class, () -> service.postTransaction(file, USERNAME));
	}

	@Test
	void givenFileWithTransactionsFromDifferentDays_whenPostTransaction_thenIgnoreDatesDifferentThanFirst() {
		final String FILE_DATA = VALID_TRANSACTION + VALID_TRANSACTION_DIFF_DATE 
				+ VALID_TRANSACTION_2 + VALID_TRANSACTION_DIFF_DATE;
		MultipartFile file = new MockMultipartFile("file", "file", "text/csv", FILE_DATA.getBytes());
	
		service.postTransaction(file, USERNAME);
		
		List<Transaction> list = transactionRepository.findByBancoOrigem("BYTE BANK").get();
		assertTrue(list.size() == 2);
	}
	
	@Test
	void givenFileWithTransactionsWithDateAlreadyRegistered_whenPostTransaction_thenThrowsException() {
		MultipartFile old_file = new MockMultipartFile("file", "file", "text/csv", VALID_TRANSACTION.getBytes());
		
		service.postTransaction(old_file, USERNAME);
		
		MultipartFile new_file = new MockMultipartFile("file", "file", "text/csv", VALID_TRANSACTION.getBytes());
		
		assertThrows(InvalidFileException.class, () -> service.postTransaction(new_file, USERNAME));
	}
}
