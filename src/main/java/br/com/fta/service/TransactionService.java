package br.com.fta.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	
	public List<ImportInfo> transactions() {
		List<ImportInfo> list = importInfoRepository.findAll();
		list.sort(Collections.reverseOrder());
		return list;
	}
	
	public void postTransaction(MultipartFile file) {
		if (file.isEmpty()) {
			throw new RuntimeException("File empty.");
		}
				
		try (Scanner scanner = new Scanner(file.getInputStream())){
			LocalDate transactionsDate = null;
			
			while (scanner.hasNextLine()) {
				String[] transactionInfo = scanner.nextLine().split(",");
				
				String bancoOrigem = transactionInfo[0];
				String agenciaOrigem = transactionInfo[1];
				String contaOrigem = transactionInfo[2];
				String bancoDestino = transactionInfo[3];
				String agenciaDestino = transactionInfo[4];
				String contaDestino = transactionInfo[5];
				String valorTransacao = transactionInfo[6];
				LocalDateTime date = LocalDateTime.parse(transactionInfo[7]);
				
				if (transactionsDate == null) {
					transactionsDate = date.toLocalDate();
					
					if (!transactionRepository.findByDateBetween(
							transactionsDate.atStartOfDay(), 
							transactionsDate.atTime(23, 59, 59))
							.get().isEmpty()) {
						throw new RuntimeException("Data from this day already uploaded.");
					}
				}
				
				Transaction  transaction = new Transaction();
				transaction.setBancoOrigem(bancoOrigem);
				transaction.setAgenciaOrigem(agenciaOrigem);
				transaction.setContaOrigem(contaOrigem);
				transaction.setBancoDestino(bancoDestino);
				transaction.setAgenciaDestino(agenciaDestino);
				transaction.setContaDestino(contaDestino);
				transaction.setValorTransacao(valorTransacao);
				transaction.setDate(date);
				
				Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate(transaction);
				if (!constraintViolations.isEmpty()) {
					System.out.println("Invalid transaction.");
					continue;
				}			
				
				if (!transactionsDate.equals(date.toLocalDate())) {
					System.out.println("Wrong date.");			
					continue;
				}
				
				transactionRepository.save(transaction);
			}
			ImportInfo importInfo = new ImportInfo(LocalDateTime.now(), transactionsDate);
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

}
