package br.com.fta.transaction.domain;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperXMLTest {
	
	private final TransactionMapperXML mapper = new TransactionMapperXML();

	@Test
	void givenValidFile_whenMap_thenReturnTransaction() throws IOException {
		String VALID = "<transactions><transaction>"
				+ "<origin><bank>Foo</bank><agency>0001</agency>"
				+ "<account>00001-1</account></origin><destination>"
				+ "<bank>Bar</bank><agency>0001</agency>"
				+ "<account>00001-1</account></destination><value>100</value>"
				+ "<date>2022-01-02T16:30:00</date></transaction></transactions>";
		MockMultipartFile file =
				new MockMultipartFile("file", VALID.getBytes());
		
		List<Transaction> transactions = mapper.map(file.getInputStream());
		
		Transaction transaction = transactions.get(0);
		BankAccount origin = transaction.getOrigin();
		BankAccount destination = transaction.getDestination();
		assertEquals("Foo", origin.getBank());
		assertEquals("0001", origin.getAgency());
		assertEquals("00001-1", origin.getAccount());
		assertEquals("Bar", destination.getBank());
		assertEquals("0001", destination.getAgency());
		assertEquals("00001-1", destination.getAccount());
		assertEquals("100", transaction.getValue());
		assertEquals(LocalDateTime.of(2022, 1, 2, 16, 30), transaction.getDate());
	}
	
	@Test
	void givenInvalidDate_whenMapToTransaction_thenThrowsException() throws IOException {
		String INVALID_DATE = "<transactions><transaction>"
				+ "<origin><bank>Foo</bank><agency>0001</agency>"
				+ "<account>00001-1</account></origin><destination>"
				+ "<bank>Bar</bank><agency>0001</agency>"
				+ "<account>00001-1</account></destination><value>100</value>"
				+ "<date>2022/01/02T16:30:00</date></transaction></transactions>";
		MockMultipartFile file =
				new MockMultipartFile("file", INVALID_DATE.getBytes());
		
		assertThrows(InvalidFileException.class, () -> mapper.map(file.getInputStream()));
	}
	
	@Test
	void givenMissingInformation_whenMapToTransaction_thenReturnTransactionWithNullValues() throws IOException {
		String MISSING_INFORMATION = "<transactions><transaction>"
				+ "<origin><bank>Foo</bank><agency>0001</agency>"
				+ "<account>00001-1</account></origin><destination>"
				+ "</destination><value>100</value>"
				+ "<date>2022-01-02T16:30:00</date></transaction></transactions>";
		MockMultipartFile file =
				new MockMultipartFile("file", MISSING_INFORMATION.getBytes());
		List<Transaction> transactions = mapper.map(file.getInputStream());
		Transaction transaction = transactions.get(0);
		
		assertEquals("Foo", transaction.getOrigin().getBank());
		assertNull(transaction.getDestination().getAgency());
	}
}
