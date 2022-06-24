package br.com.fta.transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
			ImportInfo importInfo = new ImportInfo(
											LocalDateTime.now(),
											analyzer.getDateOfTransactions(),
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
		LocalDate date = LocalDate.parse(dateString);

		Optional<ImportInfo> importInfoOptional = importInfoRepository.findByTransactionsDate(date);
		ImportInfo importInfo = importInfoOptional.get();
		return importInfo;
	}
}
