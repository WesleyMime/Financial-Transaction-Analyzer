package br.com.fta.transaction.importinfo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ImportInfo implements Comparable<ImportInfo>{

	@Id
	private String id;
	private LocalDateTime importRealized;
	private LocalDate transactionsDate;

	public ImportInfo(LocalDateTime importRealized, LocalDate transactionsDate) {
		this.importRealized = importRealized;
		this.transactionsDate = transactionsDate;
		
	}
	
	public LocalDateTime getImportRealized() {
		return importRealized;
	}
	
	public LocalDate getTransactionsDate() {
		return transactionsDate;
	}

	@Override
	public int compareTo(ImportInfo i) {
		return this.transactionsDate.compareTo(i.transactionsDate);
	}
}
