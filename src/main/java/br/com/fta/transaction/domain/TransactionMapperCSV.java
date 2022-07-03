package br.com.fta.transaction.domain;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.fta.shared.infra.Mapper;

public class TransactionMapperCSV implements Mapper<InputStream, List<Transaction>> {

	public List<Transaction> map(InputStream data) {
		List<Transaction> list = new ArrayList<>();

		Scanner scanner = new Scanner(data);
		while (scanner.hasNextLine()) {
			try {
				String nextLine = scanner.nextLine();
				String[] transactionInfo = nextLine.split(",");

				String bancoOrigem = transactionInfo[0];
				String agenciaOrigem = transactionInfo[1];
				String contaOrigem = transactionInfo[2];
				String bancoDestino = transactionInfo[3];
				String agenciaDestino = transactionInfo[4];
				String contaDestino = transactionInfo[5];
				String valorTransacao = transactionInfo[6];
				LocalDateTime date = LocalDateTime.parse(transactionInfo[7]);

				list.add(new Transaction(bancoOrigem, agenciaOrigem, contaOrigem, bancoDestino, agenciaDestino,
						contaDestino, valorTransacao, date));
			} catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		scanner.close();
		return list;

	}
}
