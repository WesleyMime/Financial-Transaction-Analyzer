package br.com.fta.transaction.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.shared.exceptions.ResourceNotFoundException;
import br.com.fta.transaction.domain.ImportInfo;
import br.com.fta.transaction.domain.InvalidFileException;
import br.com.fta.transaction.domain.Transaction;
import br.com.fta.transaction.domain.TransactionAnalyzer;
import br.com.fta.transaction.infra.ImportInfoRepository;
import br.com.fta.transaction.infra.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ImportInfoRepository importInfoRepository;
	@Autowired
	private TransactionAnalyzer analyzer;

	public List<ImportInfo> transactions() {
		List<ImportInfo> list = importInfoRepository.findAll();
		list.sort(Collections.reverseOrder());
		return list;
	}

	public void postTransaction(MultipartFile file, String username) {
		if (file.isEmpty()) {
			throw new InvalidFileException("Empty file.");
		}
		
		Set<Transaction> transaction = analyzer.analyzeTransaction(file);

		ImportInfo importInfo = new ImportInfo(
										LocalDateTime.now(),
										analyzer.getDateOfTransactions(),
										username);
		transactionRepository.saveAll(transaction);
		importInfoRepository.save(importInfo);
	}

	public void deleteTransactions() {
		transactionRepository.deleteAll();
		importInfoRepository.deleteAll();
	}

	public List<Transaction> detailTransactions(LocalDate date) {
		LocalDateTime startDay = date.atStartOfDay();
		LocalDateTime endDay = date.atTime(23, 59, 59);

		Optional<List<Transaction>> optional = transactionRepository.findByDateBetween(startDay, endDay);
		List<Transaction> transactions = optional.get();
		return transactions;
	}

	public ImportInfo detailImport(String dateString) {
		try {
			LocalDate date = LocalDate.parse(dateString);
			
			Optional<ImportInfo> importInfoOptional = importInfoRepository.findByTransactionsDate(date);
			return importInfoOptional.get();
		}
		catch (DateTimeParseException | NoSuchElementException e) {
			throw new ResourceNotFoundException();
		}
	}
}
