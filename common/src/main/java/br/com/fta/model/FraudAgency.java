package br.com.fta.model;

import java.math.BigDecimal;

public record FraudAgency (String type, BankAgency agency, BigDecimal value) {
}
