package br.com.fta.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.exception.InvalidFileException;
import br.com.fta.exception.InvalidTransactionException;
import br.com.fta.model.ImportInfo;
import br.com.fta.model.Transaction;
import br.com.fta.repository.ImportInfoRepository;
import br.com.fta.repository.TransactionRepository;

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
	
	public void postTransaction(MultipartFile file) {
		analyzer.checkFile(file);
				
		try (Scanner scanner = new Scanner(file.getInputStream())){
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
			ImportInfo importInfo = new ImportInfo(LocalDateTime.now(), analyzer.getDateOfTransactions());
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
		
		if (analyzer.firstTransaction()) {
			analyzer.setDateOfTransactions(dateOfTransactions);
			if (!transactionRepository.findByDateBetween(
					dateOfTransactions.atStartOfDay(),
					dateOfTransactions.atTime(23, 59, 59))
					.get().isEmpty()) {
				throw new InvalidFileException("Data from this day already uploaded.");
			}
		}
	}

}
