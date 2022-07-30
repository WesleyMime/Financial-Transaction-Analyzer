package br.com.fta.model;

import java.math.BigDecimal;

public record FraudAccount(String type, BankAccount account, BigDecimal value) {
}
