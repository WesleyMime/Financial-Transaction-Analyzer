package br.com.fta.transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.shared.exceptions.ResourceNotFoundException;
import br.com.fta.transaction.exceptions.InvalidFileException;
import br.com.fta.transaction.exceptions.InvalidTransactionException;
import br.com.fta.transaction.importinfo.ImportInfo;
import br.com.fta.transaction.importinfo.ImportInfoRepository;

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
		analyzer.checkFile(file);

		try (Scanner scanner = new Scanner(file.getInputStream())) {
			analyzer.setDateOfTransactions(null);

			while (scanner.hasNextLine()) {
				try {
					Transaction transaction = analyzer.analyzeTransaction(scanner.nextLine());

					checkFirstTransaction(transaction.getDate());

					transactionRepository.save(transaction);
				} catch (InvalidTransactionException e) {
					continue;
				}
			}
			
			LocalDate dateOfTransactions = analyzer.getDateOfTransactions();
			if (dateOfTransactions == null) {
				throw new InvalidFileException("Invalid file.");
			}
			ImportInfo importInfo = new ImportInfo(
											LocalDateTime.now(),
											dateOfTransactions,
											username);
			importInfoRepository.save(importInfo);

		} catch (IOException e) {
			System.out.println("Error in scanner");
			e.printStackTrace();
		}

	}

	public void deleteTransactions() {
		transactionRepository.deleteAll();
		importInfoRepository.deleteAll();
	}

	private void checkFirstTransaction(LocalDateTime localDateTime) {
		LocalDate dateOfTransactions = localDateTime.toLocalDate();
		LocalDateTime startDay = dateOfTransactions.atStartOfDay();
		LocalDateTime endDay = dateOfTransactions.atTime(23, 59, 59);

		if (analyzer.firstTransaction()) {
			analyzer.setDateOfTransactions(dateOfTransactions);
			if (!transactionRepository.findByDateBetween(startDay, endDay)
							.get().isEmpty()) {
				throw new InvalidFileException("Data from this day already uploaded.");
			}
		}
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
