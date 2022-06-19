package br.com.fta.transaction;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction {
	
	@Id
	private String id;
	@NotBlank
	private String bancoOrigem;
	@NotBlank
	private String agenciaOrigem;
	@NotBlank
	private String contaOrigem;
	@NotBlank
	private String bancoDestino;
	@NotBlank
	private String agenciaDestino;
	@NotBlank
	private String contaDestino;
	@NotBlank
	private String valorTransacao;
	@NotNull
	private LocalDateTime date;
	
	public Transaction(@NotBlank String bancoOrigem, @NotBlank String agenciaOrigem, @NotBlank String contaOrigem,
			@NotBlank String bancoDestino, @NotBlank String agenciaDestino, @NotBlank String contaDestino,
			@NotBlank String valorTransacao, @NotNull LocalDateTime date) {
		this.bancoOrigem = bancoOrigem;
		this.agenciaOrigem = agenciaOrigem;
		this.contaOrigem = contaOrigem;
		this.bancoDestino = bancoDestino;
		this.agenciaDestino = agenciaDestino;
		this.contaDestino = contaDestino;
		this.valorTransacao = valorTransacao;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public String getBancoOrigem() {
		return bancoOrigem;
	}
	public String getAgenciaOrigem() {
		return agenciaOrigem;
	}
	public String getContaOrigem() {
		return contaOrigem;
	}
	public String getBancoDestino() {
		return bancoDestino;
	}
	public String getAgenciaDestino() {
		return agenciaDestino;
	}
	public String getContaDestino() {
		return contaDestino;
	}
	public String getValorTransacao() {
		return valorTransacao;
	}
	public LocalDateTime getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", bancoOrigem=" + bancoOrigem + ", agenciaOrigem=" + agenciaOrigem
				+ ", contaOrigem=" + contaOrigem + ", bancoDestino=" + bancoDestino + ", agenciaDestino="
				+ agenciaDestino + ", contaDestino=" + contaDestino + ", valorTransacao=" + valorTransacao + ", date="
				+ date + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(agenciaDestino, agenciaOrigem, bancoDestino, bancoOrigem, contaDestino, contaOrigem, date,
				valorTransacao);
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
		return Objects.equals(agenciaDestino, other.agenciaDestino)
				&& Objects.equals(agenciaOrigem, other.agenciaOrigem)
				&& Objects.equals(bancoDestino, other.bancoDestino) && Objects.equals(bancoOrigem, other.bancoOrigem)
				&& Objects.equals(contaDestino, other.contaDestino) && Objects.equals(contaOrigem, other.contaOrigem)
				&& Objects.equals(date, other.date) && Objects.equals(valorTransacao, other.valorTransacao);
	}
	
	
	
}
