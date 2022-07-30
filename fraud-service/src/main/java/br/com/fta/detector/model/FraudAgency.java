package br.com.fta.detector.model;

import br.com.fta.model.BankAgency;

import java.math.BigDecimal;

public record FraudAgency (String type, BankAgency agency, BigDecimal value) {
}
