package br.com.fta.transaction.domain;

import br.com.fta.shared.infra.Mapper;
import br.com.fta.transaction.infra.TransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TransactionAnalyzer {

	private final Validator validator;

	private LocalDate dateOfTransactions;
	
	private Mapper<InputStream, List<Transaction>> mapper;
	
	private final TransactionRepository repository;
	
	public TransactionAnalyzer(TransactionRepository repository) {
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
		this.repository = repository;
	}

	public LocalDate getDateOfTransactions() {
		return dateOfTransactions;
	}
	
	public Set<Transaction> analyzeTransaction(MultipartFile file) {
		this.dateOfTransactions = null;
		
		try {
			mapperForFileType(file);
			
			List<Transaction> transactions = mapper.map(file.getInputStream());
			Set<Transaction> validTransactions = new HashSet<>();
			
			transactions.forEach(transaction -> {
				Set<ConstraintViolation<Transaction>> validate = validator.validate(transaction);
	
				try {
					if (!validate.isEmpty()) {
						throw new InvalidTransactionException("Invalid value.");
					}
		
					if (firstTransaction()) {
						validateDateOfFirstTransaction(transaction.getDate());
					}
					else {
						checkDateOfTransactions(transaction.getDate());
					}
					validTransactions.add(transaction);
				} catch (InvalidTransactionException e) {
					System.out.println(e.getMessage());
				}
				
			});
			
			if (dateOfTransactions == null) {
				throw new InvalidFileException("File with no valid transactions.");
			}
			
			return validTransactions;

		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidFileException("Invalid file.");
		}
	}

	private void mapperForFileType(MultipartFile file) {
		try {
			String contentType = file.getContentType();
			String typeString = contentType.substring(contentType.indexOf("/") + 1).toUpperCase();
			FileTypes type = FileTypes.valueOf(typeString);
		
			mapper = type.mapper();
		} catch (RuntimeException e){
			List<String> fileTypesNameList = Arrays.stream(FileTypes.values()).map(Enum::name).toList();
			throw new InvalidFileException("File not supported. Supported files: " + fileTypesNameList);
		}
	}

	private boolean firstTransaction() {
		return dateOfTransactions == null;
	}
	
	private void validateDateOfFirstTransaction(LocalDateTime localDateTime) {
		this.dateOfTransactions = localDateTime.toLocalDate();
		LocalDateTime startDay = dateOfTransactions.atStartOfDay();
		LocalDateTime endDay = dateOfTransactions.atTime(23, 59, 59);

		if (!repository.findByDateBetween(startDay, endDay)
						.get().isEmpty()) {
			throw new InvalidFileException("Transactions from this day already uploaded.");
		}
	}

	private void checkDateOfTransactions(LocalDateTime localDateTime) {
		LocalDate date = localDateTime.toLocalDate();
		
		if (!this.dateOfTransactions.equals(date)) {
			throw new InvalidTransactionException("Wrong date.");
		}
	}

}