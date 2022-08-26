package br.com.fta.fraud.detector.model;

import br.com.fta.transaction.domain.BankAccount;

import java.math.BigDecimal;

public record FraudAccount(String type, BankAccount account, BigDecimal value) {
}