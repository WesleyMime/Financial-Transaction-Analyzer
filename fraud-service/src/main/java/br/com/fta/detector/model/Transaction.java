package br.com.fta.detector.model;

import java.time.LocalDateTime;

public record Transaction (BankAccount origin, BankAccount destination, String value, LocalDateTime date) {}
