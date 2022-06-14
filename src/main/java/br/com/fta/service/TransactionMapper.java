package br.com.fta.service;

import java.time.LocalDateTime;

import br.com.fta.model.Transaction;

public class TransactionMapper{

	public static Transaction map(String data) {
		String[] transactionInfo = data.split(",");
		
		String bancoOrigem = transactionInfo[0];
		String agenciaOrigem = transactionInfo[1];
		String contaOrigem = transactionInfo[2];
		String bancoDestino = transactionInfo[3];
		String agenciaDestino = transactionInfo[4];
		String contaDestino = transactionInfo[5];
		String valorTransacao = transactionInfo[6];
		LocalDateTime date = LocalDateTime.parse(transactionInfo[7]);
		
		return new Transaction(bancoOrigem, agenciaOrigem, contaOrigem, bancoDestino,
				agenciaDestino, contaDestino, valorTransacao, date);
	}
}
