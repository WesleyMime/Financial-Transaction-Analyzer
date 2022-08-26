package br.com.fta.fraud.detector.model;

import java.math.BigDecimal;

public record FraudAgency (String type, BankAgency agency, BigDecimal value) {
}
