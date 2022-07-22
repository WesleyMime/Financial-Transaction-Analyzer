package br.com.fta.detector.model;

import java.math.BigDecimal;

public record FraudAccount(String type, BankAccount account, BigDecimal value) {
}
