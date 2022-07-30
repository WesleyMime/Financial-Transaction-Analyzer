package br.com.fta.model;

import java.time.LocalDateTime;

public record Transaction (BankAccount origin, BankAccount destination, String value, LocalDateTime date) {}
