package br.com.fta.fraudDetector.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.com.fta.fraudDetector.model.BankAgency;
import br.com.fta.fraudDetector.model.FraudDetector;
import br.com.fta.transaction.domain.BankAccount;
import br.com.fta.transaction.domain.Transaction;

@Service
public class FraudDetectorService {
	
	private FraudDetector fraudDetector = new FraudDetector();
	
	public void detectFrauds(List<Transaction> transactions, Model model) {
		List<Transaction> fraudTransactions = 
				fraudDetector.analyzeTransactions(transactions);
		
		Map<String, Map<BankAccount, BigDecimal>> fraudAccounts = 
				fraudDetector.analyzeBankAccounts(transactions);
		
		Map<String, Map<BankAgency, BigDecimal>> fraudAgencies = 
				fraudDetector.analyzeAgency(transactions);
		
		model.addAttribute("transactions", fraudTransactions);
		model.addAttribute("entryExitAccounts", fraudAccounts);
		model.addAttribute("entryExitAgencies", fraudAgencies);
		
	}
}
