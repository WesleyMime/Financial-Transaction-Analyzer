package br.com.fta.detector.model;

import java.util.List;
import java.util.Set;

public record Frauds(List<Transaction> fraudTransactions,
                     Set<FraudAccount> fraudAccounts, Set<FraudAgency> fraudAgencies) {
}

