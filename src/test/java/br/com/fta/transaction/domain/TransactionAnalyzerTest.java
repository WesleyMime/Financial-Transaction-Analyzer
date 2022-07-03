package br.com.fta.transaction.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import br.com.fta.transaction.domain.InvalidFileException;
import br.com.fta.transaction.domain.Transaction;
import br.com.fta.transaction.domain.TransactionAnalyzer;
import br.com.fta.transaction.infra.ImportInfoRepository;
import br.com.fta.transaction.infra.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionAnalyzerTest {
	
	@Mock
	private ImportInfoRepository importInfoRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@InjectMocks
	private TransactionAnalyzer analyzer;
	
	private final String XML_VALID = "<transactions><transaction>\n"
				+ "<bancoOrigem>Foo</bancoOrigem>\n<agenciaOrigem>0001</agenciaOrigem>\n"
				+ "<contaOrigem>00001-1</contaOrigem>\n<bancoDestino>Bar</bancoDestino>\n"
				+ "<agenciaDestino>0001</agenciaDestino>\n<contaDestino>00001-1</contaDestino>\n"
				+ "<valorTransacao>100</valorTransacao>\n<date>2022-01-02T16:30:00</date>\n"
				+ "</transaction></transactions>";

	private final String XML_MISSING_INFORMATION = "<transactions><transaction>\n"
				+ "	<bancoOrigem>Foo</bancoOrigem>\n<agenciaOrigem>0001</agenciaOrigem>\n"
				+ "<contaOrigem>00001-1</contaOrigem>\n<bancoDestino>Bar</bancoDestino>\n"
				+ "<date>2022-01-02T16:30:00</date>\n</transaction></transactions>";

	private final String XML_INVALID_VALUE = "<transactions><transaction>\n"
				+ "<bancoOrigem>Foo</bancoOrigem>\n<agenciaOrigem>0001</agenciaOrigem>\n"
				+ "<contaOrigem>00001-1</contaOrigem>\n<bancoDestino></bancoDestino>\n"
				+ "<agenciaDestino></agenciaDestino>\n<contaDestino>00001-1</contaDestino>\n"
				+ "<valorTransacao>100</valorTransacao>\n<date>2022-01-02T16:30:00</date>\n"
				+ "</transaction></transactions>";
	
	private final String CSV_VALID = "Foo,0001,00001-1,Bar,0001,00001-1,100,2022-01-02T16:30:00";
	private final String CSV_MISSING_INFORMATION = "Foo,0001,00001-1,0001,00001-1,100,2022-01-02T16:30:00";
	private final String CSV_INVALID_VALUE = "Foo,0001,00001-1,,0001,00001-1,100,2022-01-02T16:30:00";
	
	private final Transaction TRANSACTION_EXPECTED = new Transaction("Foo", "0001", "00001-1", "Bar", "0001",
			"00001-1", "100", LocalDateTime.of(2022, 01, 02, 16, 30));
	
	@Test
	void givenValidCSVFile_whenValidate_thenReturnTransaction() {
		when(transactionRepository.findByDateBetween(any(), any()))
		.thenReturn(Optional.of(List.of()));
		
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/csv", CSV_VALID.getBytes());
		
		Set<Transaction> transactions = analyzer.analyzeTransaction(file);
		
		assertEquals(1, transactions.size());
		assertTrue(transactions.contains(TRANSACTION_EXPECTED));		
	}
	
	@Test
	void givenCSVFileWithOneValidAndOneInvalid_whenValidate_thenReturnOneTransaction() {
		when(transactionRepository.findByDateBetween(any(), any()))
		.thenReturn(Optional.of(List.of()));
		
		String data = CSV_INVALID_VALUE + "\n" + CSV_VALID;
		
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/csv", data.getBytes());
		
		Set<Transaction> transactions = analyzer.analyzeTransaction(file);
		
		assertEquals(1, transactions.size());
		assertTrue(transactions.contains(TRANSACTION_EXPECTED));		
	}
	
	@Test
	void givenValidXMLFile_whenValidate_thenReturnTransaction() {
		when(transactionRepository.findByDateBetween(any(), any()))
		.thenReturn(Optional.of(List.of()));
		
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/xml", XML_VALID.getBytes());
		
		Set<Transaction> transactions = analyzer.analyzeTransaction(file);
		
		assertEquals(1, transactions.size());
		assertTrue(transactions.contains(TRANSACTION_EXPECTED));		
	}
	
	@Test
	void givenCSVFileWithMissingInformation_whenValidate_thenThrowsException() {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/csv", CSV_MISSING_INFORMATION.getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
	
	@Test
	void givenXMLFileWithMissingInformation_whenValidate_thenThrowsException() {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/xml", XML_MISSING_INFORMATION.getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
	
	@Test
	void givenCSVFileWithInvalidValue_whenValidate_thenThrowsException() {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/csv", CSV_INVALID_VALUE.getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
	
	@Test
	void givenXMLFileWithInvalidValue_whenValidate_thenThrowsException() {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/xml", XML_INVALID_VALUE.getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
	
	@Test
	void givenRandonString_whenValidate_thenThrowsException() {
		final String DATA_TRANSACTION = "43gdsg,w,e,9,c,a,d,5,f1631d2h4955ub52a3ce20946c505a";
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text/csv", DATA_TRANSACTION.getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
	
	@Test
	void givenContentTypeNotSupported_whenAnalyze_thenThrowsException() {
		MockMultipartFile file = 
				new MockMultipartFile("file", "file" , "text", "".getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
}
