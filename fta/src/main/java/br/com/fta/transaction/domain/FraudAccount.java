package br.com.fta.transaction.domain;

import java.math.BigDecimal;

public record FraudAccount(String type, BankAccount account, BigDecimal value) {
}
