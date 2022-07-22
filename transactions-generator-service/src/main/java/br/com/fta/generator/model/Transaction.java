package br.com.fta.generator.model;

import java.time.LocalDateTime;

public record Transaction (BankAccount origin, BankAccount destination, String value, LocalDateTime date) {}
