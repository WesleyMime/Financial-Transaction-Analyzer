package br.com.fta.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

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
	
	public void setId(String id) {
		this.id = id;
	}
	public void setBancoOrigem(String bancoOrigem) {
		this.bancoOrigem = bancoOrigem;
	}
	public void setAgenciaOrigem(String agenciaOrigem) {
		this.agenciaOrigem = agenciaOrigem;
	}
	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public void setBancoDestino(String bancoDestino) {
		this.bancoDestino = bancoDestino;
	}
	public void setAgenciaDestino(String agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	public void setValorTransacao(String valorTransacao) {
		this.valorTransacao = valorTransacao;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", bancoOrigem=" + bancoOrigem + ", agenciaOrigem=" + agenciaOrigem
				+ ", contaOrigem=" + contaOrigem + ", bancoDestino=" + bancoDestino + ", agenciaDestino="
				+ agenciaDestino + ", contaDestino=" + contaDestino + ", valorTransacao=" + valorTransacao + ", date="
				+ date + "]";
	}
	
}
