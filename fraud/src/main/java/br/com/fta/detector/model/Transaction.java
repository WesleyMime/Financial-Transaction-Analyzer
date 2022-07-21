package br.com.fta.detector.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record Transaction (BankAccount origin, BankAccount destination, String value,
                           @NotNull LocalDateTime date) {}
