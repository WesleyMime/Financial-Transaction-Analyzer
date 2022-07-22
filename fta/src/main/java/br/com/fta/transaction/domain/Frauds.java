package br.com.fta.transaction.domain;

import java.util.List;
import java.util.Set;

public record Frauds(List<Transaction> fraudTransactions,
                     Set<FraudAccount> fraudAccounts, Set<FraudAgency> fraudAgencies) {
}

