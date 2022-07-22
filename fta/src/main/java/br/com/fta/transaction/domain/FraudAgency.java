package br.com.fta.transaction.domain;

import java.math.BigDecimal;

public record FraudAgency (String type, BankAgency agency, BigDecimal value) {
}
