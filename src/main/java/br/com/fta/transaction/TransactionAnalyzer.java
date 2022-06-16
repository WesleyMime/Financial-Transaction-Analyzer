package br.com.fta.transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.fta.transaction.exceptions.InvalidFileException;
import br.com.fta.transaction.exceptions.InvalidTransactionException;

@Component
public class TransactionAnalyzer {

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();

	private LocalDate dateOfTransactions = null;

	public void checkFile(MultipartFile file) {
		if (file.isEmpty()) {
			throw new InvalidFileException("File empty.");
		}
	}

	public Transaction analyzeTransaction(String transactionData) {
		try {
			Transaction transaction = TransactionMapper.map(transactionData);
			Set<ConstraintViolation<Transaction>> validate = validator.validate(transaction);

			if (!validate.isEmpty()) {
				throw new InvalidTransactionException("Invalid value.");
			}

			if (!firstTransaction()) {
				checkDateOfTransactions(transaction.getDate());
			}
			return transaction;

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidTransactionException("Missing information.");
		}
	}

	public LocalDate getDateOfTransactions() {
		return dateOfTransactions;
	}
	
	public void setDateOfTransactions(LocalDate dateOfTransactions) {
		this.dateOfTransactions = dateOfTransactions;
	}

	
	public boolean firstTransaction() {
		return dateOfTransactions == null;
	}

	private void checkDateOfTransactions(LocalDateTime localDateTime) {
		LocalDate date = localDateTime.toLocalDate();
		
		if (!this.dateOfTransactions.equals(date)) {
			throw new InvalidTransactionException("Wrong date.");
		}
	}
}