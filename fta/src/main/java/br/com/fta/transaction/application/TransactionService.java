package br.com.fta.transaction.application;

import br.com.fta.model.Frauds;
import br.com.fta.shared.exceptions.ResourceNotFoundException;
import br.com.fta.shared.exceptions.ServiceUnavailableException;
import br.com.fta.transaction.domain.ImportInfo;
import br.com.fta.transaction.domain.InvalidFileException;
import br.com.fta.transaction.domain.Transaction;
import br.com.fta.transaction.domain.TransactionAnalyzer;
import br.com.fta.transaction.infra.FraudClient;
import br.com.fta.transaction.infra.GeneratorClient;
import br.com.fta.transaction.infra.ImportInfoRepository;
import br.com.fta.transaction.infra.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ImportInfoRepository importInfoRepository;
	@Autowired
	private TransactionAnalyzer analyzer;
	@Autowired
	private FraudClient fraudClient;

	@Autowired
	private GeneratorClient generatorClient;

	public Page<ImportInfo> transactions(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page -1, size, Sort.by("transactionsDate").descending());
		return importInfoRepository.findAll(pageRequest);
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

		return transactionRepository.findByDateBetween(startDay, endDay).get();
	}

	public ImportInfo detailImport(String dateString) {
		try {
			LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);

			Optional<ImportInfo> importInfoOptional = importInfoRepository.findByTransactionsDate(date);
			return importInfoOptional.get();
		} catch (RuntimeException e) {
			throw new ResourceNotFoundException();
		}
	}

	public Frauds report(LocalDate startOfMonth) {
		LocalDateTime start = LocalDateTime.of(startOfMonth, LocalTime.of(0, 0));

		LocalDate endOfMonth = LocalDate.of(
				startOfMonth.getYear(), startOfMonth.getMonthValue(), startOfMonth.lengthOfMonth());
		LocalDateTime end = LocalDateTime.of(endOfMonth, LocalTime.of(23, 59));

		List<Transaction> transactions = transactionRepository.findByDateBetween(start, end).get();
		if (transactions.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return fraudClient.detectFrauds(transactions);

	}

	public void generateTransactions(String username) {
		List<Transaction> list = List.of();
		try {
			list = generatorClient.generateTransactions();
		} catch (RuntimeException e) {
			throw new ServiceUnavailableException("Unable to generate transactions, please try again later.");
		}
		transactionRepository.saveAll(list);

		ImportInfo importInfo = new ImportInfo(
				LocalDateTime.now(),
				list.get(0).getDate().toLocalDate(),
				username);

		importInfoRepository.save(importInfo);
	}
}
