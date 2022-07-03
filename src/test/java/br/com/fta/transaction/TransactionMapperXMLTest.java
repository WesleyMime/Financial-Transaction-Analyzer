package br.com.fta.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import br.com.fta.transaction.exceptions.InvalidTransactionException;

class TransactionMapperXMLTest {
	
	private final TransactionMapperXML mapper = new TransactionMapperXML();
	
	private final String VALID = "<transaction>\n<bancoOrigem>Foo</bancoOrigem>\n"
			+ "<agenciaOrigem>0001</agenciaOrigem>\n<contaOrigem>00001-1</contaOrigem>\n"
			+ "<bancoDestino>Bar</bancoDestino>\n<agenciaDestino>0001</agenciaDestino>\n"
			+ "<contaDestino>00001-1</contaDestino>\n<valorTransacao>100</valorTransacao>\n"
			+ "<date>2022-01-02T16:30:00</date>\n</transaction>";

	private String MISSING_INFORMATION = "<transaction>\n"
				+ "	<bancoOrigem>Foo</bancoOrigem>\n<agenciaOrigem>0001</agenciaOrigem>\n"
				+ "<contaOrigem>00001-1</contaOrigem>\n<bancoDestino>Bar</bancoDestino>\n"
				+ "<date>2022-01-02T16:30:00</date>\n</transaction>";

	private final String INVALID_DATE = "<transaction>\n<bancoOrigem>Foo</bancoOrigem>\n"
				+ "<agenciaOrigem>0001</agenciaOrigem>\n<contaOrigem>00001-1</contaOrigem>\n"
				+ "<bancoDestino>Bar</bancoDestino>\n<agenciaDestino>0001</agenciaDestino>\n"
				+ "<contaDestino>00001-1</contaDestino>\n<valorTransacao>100</valorTransacao>\n"
				+ "<date>2022/01/02T16:30:00</date>\n</transaction>";

	@Test
	void givenValidFile_whenMap_thenReturnTransaction() throws IOException {
		String file_data = "<transactions>" + VALID + "</transactions>";
		
		MockMultipartFile file = 
				new MockMultipartFile("file", file_data.getBytes());
		
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
	void givenInvalidDate_whenMapToTransaction_thenThrowsException() throws IOException {
		String file_data = "<transactions>" + INVALID_DATE + "</transactions>";
		
		MockMultipartFile file = 
				new MockMultipartFile("file", file_data.getBytes());
		
		assertThrows(InvalidTransactionException.class, () -> mapper.map(file.getInputStream()));
	}
	
	@Test
	void givenMissingInformation_whenMapToTransaction_thenReturnTransactionWithNullValues() throws IOException {
		String file_data = "<transactions>" + MISSING_INFORMATION + "</transactions>";
		
		MockMultipartFile file = 
				new MockMultipartFile("file", file_data.getBytes());
		List<Transaction> transactions = mapper.map(file.getInputStream());
		Transaction transaction = transactions.get(0);
		
		assertEquals("Foo", transaction.getBancoOrigem());
		assertNull(transaction.getAgenciaDestino());
	}
}
