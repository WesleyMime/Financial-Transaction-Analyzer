package br.com.fta.transaction.domain;

import br.com.fta.transaction.infra.ImportInfoRepository;
import br.com.fta.transaction.infra.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionAnalyzerTest {
	
	@Mock
	private ImportInfoRepository importInfoRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@InjectMocks
	private TransactionAnalyzer analyzer;

	private final String CSV_VALID = "Foo,0001,00001-1,Bar,0001,00001-1,100,2022-01-02T16:30:00";
	private final String CSV_INVALID_VALUE = "Foo,0001,00001-1,,0001,00001-1,100,2022-01-02T16:30:00";
	
	private final Transaction TRANSACTION_EXPECTED = new Transaction(
			new BankAccount("Foo", "0001", "00001-1"), 
			new BankAccount("Bar", "0001", "00001-1"),
			"100", LocalDateTime.of(2022, 1, 2, 16, 30));
	
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

		String XML_VALID = "<transactions><transaction>"
				+ "<origin><bank>Foo</bank><agency>0001</agency>"
				+ "<account>00001-1</account></origin><destination>"
				+ "<bank>Bar</bank><agency>0001</agency>"
				+ "<account>00001-1</account></destination><value>100</value>"
				+ "<date>2022-01-02T16:30:00</date></transaction></transactions>";
		MockMultipartFile file =
				new MockMultipartFile("file", "file" , "text/xml", XML_VALID.getBytes());
		
		Set<Transaction> transactions = analyzer.analyzeTransaction(file);
		
		assertEquals(1, transactions.size());
		assertTrue(transactions.contains(TRANSACTION_EXPECTED));		
	}
	
	@Test
	void givenCSVFileWithMissingInformation_whenValidate_thenThrowsException() {
		String CSV_MISSING_INFORMATION = "Foo,0001,00001-1,0001,00001-1,100,2022-01-02T16:30:00";
		MockMultipartFile file =
				new MockMultipartFile("file", "file" , "text/csv", CSV_MISSING_INFORMATION.getBytes());
		
		assertThrows(InvalidFileException.class, () -> analyzer.analyzeTransaction(file));
	}
	
	@Test
	void givenXMLFileWithMissingInformation_whenValidate_thenThrowsException() {
		String XML_MISSING_INFORMATION = "<transactions><transaction>"
				+ "<origin><bank>Foo</bank><agency>0001</agency>"
				+ "<account>00001-1</account></origin><destination>"
				+ "</destination><value>100</value>"
				+ "<date>2022-01-02T16:30:00</date></transaction></transactions>";
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
		String XML_INVALID_VALUE = "<transactions><transaction>"
				+ "<origin><bank>Foo</bank><agency>0001</agency>"
				+ "<account>00001-1</account></origin><destination></destination><value>100</value>"
				+ "<date>2022/01/02T16:30:00</date></transaction></transactions>";
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
