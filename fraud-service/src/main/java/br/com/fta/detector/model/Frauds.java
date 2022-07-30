package br.com.fta.detector.model;

import br.com.fta.model.Transaction;

import java.util.List;
import java.util.Set;

public record Frauds(List<Transaction> fraudTransactions,
                     Set<FraudAccount> fraudAccounts, Set<FraudAgency> fraudAgencies) {
}

