package br.com.fta.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

	@Test
	void givenValidFile_whenPostTransaction_thenInsertIntoDatabase() {
		final String FILE_DATA = "BYTE BANK,0001,00001-1,BANK BYTE,0001,00001-1,"
				+ "8000,2022-01-02T07:30:00";
		MultipartFile file = new MockMultipartFile("transaction", FILE_DATA.getBytes());

		service.postTransaction(file, USERNAME);

		List<Transaction> list = transactionRepository.findByBancoOrigem("BYTE BANK").get();
		assertEquals("BANK BYTE", list.get(0).getBancoDestino());
	}
	
	@Test
	void givenFileWithInvalidValue_whenPostTransaction_thenDiscartTransaction() {
		final String FILE_DATA = "BYTE BANK INVALID,0001,00001-1,,0001,00001-1,"
				+ "8000,2022-01-02T07:30:00";
		MultipartFile file = new MockMultipartFile("transaction", FILE_DATA.getBytes());

		service.postTransaction(file, USERNAME);

		List<Transaction> list = transactionRepository.findByBancoOrigem("BYTE BANK INVALID").get();
		assertTrue(list.isEmpty());
	}

	@Test
	void givenFileWithMissingInformation_whenPostTransaction_thenDiscartTransaction() {
		final String FILE_DATA = "BYTE BANK INVALID,0001,00001-1,0001,00001-1,"
				+ "8000,2022-01-02T07:30:00";
		MultipartFile file = new MockMultipartFile("transaction", FILE_DATA.getBytes());

		service.postTransaction(file, USERNAME);

		List<Transaction> list = transactionRepository.findByBancoOrigem("BYTE BANK INVALID").get();
		assertTrue(list.isEmpty());
	}
	
	@Test
	void givenEmptyFile_whenPostTransaction_thenThrowsException() {
		final String FILE_DATA = "";
		MultipartFile file = new MockMultipartFile("transaction", FILE_DATA.getBytes());
	
		assertThrows(InvalidFileException.class, () -> service.postTransaction(file, USERNAME));
	}
	
	@Test
	void givenFileWithTransactionsFromDifferentDays_whenPostTransaction_thenIgnoreDatesDifferentThanFirst() {
		final String FILE_DATA = "BYTE BANK DATE,0001,00001-1,BANK BYTE,0001,00001-1,8000,2021-01-02T01:30:00\n" +
								"BYTE BANK DATE,0001,00001-1,BANK BYTE,0001,00001-1,8000,2021-01-03T02:30:00\n" +
								"BYTE BANK DATE,0001,00001-1,BANK BYTE,0001,00001-1,8000,2021-01-02T23:59:59\n" +
								"BYTE BANK DATE,0001,00001-1,BANK BYTE,0001,00001-1,8000,2021-02-03T04:30:00\n";
		MultipartFile file = new MockMultipartFile("transaction", FILE_DATA.getBytes());
	
		service.postTransaction(file, USERNAME);
		
		List<Transaction> list = transactionRepository.findByBancoOrigem("BYTE BANK DATE").get();
		assertTrue(list.size() == 2);
	}
	
	@Test
	void givenFileWithTransactionsWithDateAlreadyRegistered_whenPostTransaction_thenThrowsException() {
		final String OLD_TRANSACTION = "BYTE BANK,0001,00001-1,BANK BYTE,0001,00001-1,"
				+ "8000,2022-05-05T07:30:00";
		MultipartFile old_file = new MockMultipartFile("transaction", OLD_TRANSACTION.getBytes());
		
		service.postTransaction(old_file, USERNAME);
		
		final String NEW_TRANSACTION = "BYTE BANK,0001,00001-1,BANK BYTE,0001,00001-1,"
				+ "8000,2022-05-05T07:30:00";
		MultipartFile new_file = new MockMultipartFile("transaction", NEW_TRANSACTION.getBytes());
		
		assertThrows(InvalidFileException.class, () -> service.postTransaction(new_file, USERNAME));
	}
}
