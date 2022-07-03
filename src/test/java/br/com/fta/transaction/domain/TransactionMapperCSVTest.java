package br.com.fta.transaction.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import br.com.fta.shared.infra.Mapper;
import br.com.fta.transaction.domain.Transaction;
import br.com.fta.transaction.domain.TransactionMapperCSV;

class TransactionMapperCSVTest {
	
	private final String VALID = "Foo,0001,00001-1,Bar,0001,00001-1,100,2022-01-02T16:30:00";
	private final String MISSING_INFORMATION = "Foo,0001,00001-1,0001,00001-1,100,2022-01-02T16:30:00";
	private final String INVALID_DATE = "Foo,0001,00001-1,Bar,0001,00001-1,100,2022/01/02T16:30:00";
	
	private final Mapper<InputStream, List<Transaction>> mapper = new TransactionMapperCSV();

	@Test
	void givenValidFile_whenMap_thenReturnTransaction() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", VALID.getBytes());
		
		List<Transaction> transactions = mapper.map(file.getInputStream());
		
		Transaction transaction = transactions.get(0);
		assertEquals("Foo", transaction.getBancoOrigem());
		assertEquals("0001", transaction.getAgenciaOrigem());
		assertEquals("00001-1", transaction.getContaOrigem());
		assertEquals("Bar", transaction.getBancoDestino());
		assertEquals("0001", transaction.getAgenciaDestino());
		assertEquals("00001-1", transaction.getContaDestino());
		assertEquals("100", transaction.getValorTransacao());
		assertEquals(LocalDateTime.of(2022, 01, 02, 16, 30), transaction.getDate());
	}
	
	@Test
	void givenInvalidDate_whenMapToTransaction_thenReturnEmptyList() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", INVALID_DATE.getBytes());
		
		List<Transaction> transactions = mapper.map(file.getInputStream());
		assertEquals(0, transactions.size());
	}
	
	@Test
	void givenFileWithMissingInformation_whenMapToTransaction_thenReturnEmptyList() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", MISSING_INFORMATION.getBytes());
		
		List<Transaction> transactions = mapper.map(file.getInputStream());
		assertEquals(0, transactions.size());
	}
	
	@Test
	void givenFileWithTransactions_whenMapToTransaction_thenReturnListWithValidTransactions() throws IOException {
		String file_data = 
				VALID + "\n" +
				INVALID_DATE + "\n" +
				MISSING_INFORMATION + "\n"+
				VALID;
		
		MockMultipartFile file = new MockMultipartFile("file", file_data.getBytes());
		
		List<Transaction> transactions = mapper.map(file.getInputStream());
		assertEquals(2, transactions.size());
	}
}
