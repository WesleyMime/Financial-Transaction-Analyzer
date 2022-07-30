package br.com.fta.detector.model;

import br.com.fta.model.BankAccount;

import java.math.BigDecimal;

public record FraudAccount(String type, BankAccount account, BigDecimal value) {
}
