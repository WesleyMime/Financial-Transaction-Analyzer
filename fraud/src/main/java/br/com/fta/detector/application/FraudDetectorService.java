package br.com.fta.detector.application;

import br.com.fta.detector.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FraudDetectorService {
	
	private final FraudDetector fraudDetector = new FraudDetector();
	
	public Frauds detectFrauds(List<Transaction> transactions) {
		List<Transaction> fraudTransactions =
				fraudDetector.analyzeTransactions(transactions);

		Set<FraudAccount> fraudAccounts =
				fraudDetector.analyzeBankAccounts(transactions);

		Set<FraudAgency> fraudAgencies =
				fraudDetector.analyzeAgency(transactions);

		return new Frauds(fraudTransactions, fraudAccounts, fraudAgencies);
	}
}
