package br.com.fta.transaction.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction {

	@Id
	private String id;

	@Valid
	private BankAccount origin;

	@Valid
	private BankAccount destination;

	@NotBlank
	private String value;
	@NotNull
	private LocalDateTime date;
	
	public Transaction(BankAccount origin, BankAccount destination, String value,
			@NotNull LocalDateTime date) {
		this.origin = origin;
		this.destination = destination;
		this.value = value;
		this.date = date;
	}
	
	public BankAccount getOrigin() {
		return origin;
	}
	public BankAccount getDestination() {
		return destination;
	}
	public String getValue() {
		return value;
	}
	public LocalDateTime getDate() {
		return date;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", origin=" + origin + ", destination=" + destination + ", value=" + value
				+ ", date=" + date + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(date, destination, id, origin, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(date, other.date) && Objects.equals(destination, other.destination)
				&& Objects.equals(id, other.id) && Objects.equals(origin, other.origin)
				&& Objects.equals(value, other.value);
	}
}
