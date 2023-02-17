package br.com.fta.model;

import java.util.Set;

public record Frauds(Set<Transaction> fraudTransactions,
                     Set<FraudAccount> fraudAccounts, Set<FraudAgency> fraudAgencies) {
}

