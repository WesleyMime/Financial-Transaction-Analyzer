package br.com.fta.transaction.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
public class ImportInfo implements Comparable<ImportInfo>{

	@Id
	private String id;
	private final LocalDateTime importRealized;
	private final LocalDate transactionsDate;
	private final String importedBy;

	public ImportInfo(LocalDateTime importRealized, LocalDate transactionsDate, String importedBy) {
		this.importRealized = importRealized;
		this.transactionsDate = transactionsDate;
		this.importedBy = importedBy;		
	}

	public String getId() {
		return id;
	}
	
	public LocalDateTime getImportRealized() {
		return importRealized;
	}
	
	public LocalDate getTransactionsDate() {
		return transactionsDate;
	}

	public String getImportedBy() {
		return importedBy;
	}

	@Override
	public int compareTo(ImportInfo i) {
		return this.transactionsDate.compareTo(i.transactionsDate);
	}
}
