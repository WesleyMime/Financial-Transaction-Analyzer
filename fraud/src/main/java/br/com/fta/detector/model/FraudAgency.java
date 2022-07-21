package br.com.fta.detector.model;

import java.math.BigDecimal;

public record FraudAgency (String type, BankAgency agency, BigDecimal value) {
}
