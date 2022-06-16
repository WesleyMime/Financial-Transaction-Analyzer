package br.com.fta.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fta.transaction.exceptions.InvalidTransactionException;
import br.com.fta.transaction.importinfo.ImportInfoRepository;

@ExtendWith(MockitoExtension.class)
class TransactionAnalyzerTest {
	
	@Mock
	private ImportInfoRepository importInfoRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@InjectMocks
	private TransactionAnalyzer analyzer;
	
	@Test
	void givenValidTransaction_whenValidate_thenReturnTransaction() {		
		final String DATA_TRANSACTION = "BYTE BANK,0001,00001-1,BANK BYTE,0001,00001-1,8000,2022-01-02T07:30:00";
		
		Transaction actualtransaction = analyzer.analyzeTransaction(DATA_TRANSACTION);
		
		Transaction transactionExpected = new Transaction("BYTE BANK", "0001", "00001-1", "BANK BYTE", "0001",
				"00001-1", "8000", LocalDateTime.of(2022, 01, 02, 07, 30));
		assertEquals(transactionExpected, actualtransaction);		
	}
	
	@Test
	void givenTransactionWithMissingInformation_whenValidate_thenThrowsException() {
		final String DATA_TRANSACTION = "0001,00001-1,BANK BYTE,0001,00001-1,8000,2022-01-02T07:30:00";
		
		assertThrows(InvalidTransactionException.class, () -> analyzer.analyzeTransaction(DATA_TRANSACTION));
	}
	
	@Test
	void givenTransactionWithInvalidValue_whenValidate_thenThrowsException() {
		final String DATA_TRANSACTION = ",0001,00001-1,BANK BYTE,0001,8000,2022-01-02T07:30:00";
		
		assertThrows(InvalidTransactionException.class, () -> analyzer.analyzeTransaction(DATA_TRANSACTION));
	}
	
	

}
