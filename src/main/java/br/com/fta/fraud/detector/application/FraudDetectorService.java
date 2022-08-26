package br.com.fta.fraud.detector.application;

import br.com.fta.fraud.detector.model.FraudAccount;
import br.com.fta.fraud.detector.model.FraudAgency;
import br.com.fta.fraud.detector.model.FraudDetector;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

@Service
public class FraudDetectorService {
	
	private final FraudDetector fraudDetector = new FraudDetector();

	public void detectFrauds(List<Transaction> transactions, Model model) {
		List<Transaction> fraudTransactions =
				fraudDetector.analyzeTransactions(transactions);

		Set<FraudAccount> fraudAccounts =
				fraudDetector.analyzeBankAccounts(transactions);

		Set<FraudAgency> fraudAgencies =
				fraudDetector.analyzeAgency(transactions);

		model.addAttribute("transactions", fraudTransactions);
		model.addAttribute("accounts", fraudAccounts);
		model.addAttribute("agencies", fraudAgencies);

	}
}
