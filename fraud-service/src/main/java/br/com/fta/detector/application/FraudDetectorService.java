package br.com.fta.detector.application;

import br.com.fta.detector.model.FraudAccount;
import br.com.fta.detector.model.FraudAgency;
import br.com.fta.detector.model.FraudDetector;
import br.com.fta.detector.model.Frauds;
import br.com.fta.model.Transaction;
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
