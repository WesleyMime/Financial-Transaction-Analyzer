package br.com.fta.detector.model;

import br.com.fta.model.Transaction;

import java.util.Set;

public record Frauds(Set<Transaction> fraudTransactions,
                     Set<FraudAccount> fraudAccounts, Set<FraudAgency> fraudAgencies) {
}

