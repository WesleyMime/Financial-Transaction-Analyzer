package br.com.fta.transaction.domain;

import br.com.fta.shared.infra.Mapper;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionMapperCSV implements Mapper<InputStream, List<Transaction>> {

	public List<Transaction> map(InputStream data) {
		List<Transaction> list = new ArrayList<>();

		Scanner scanner = new Scanner(data);
		while (scanner.hasNextLine()) {
			try {
				String nextLine = scanner.nextLine();
				String[] transactionInfo = nextLine.split(",");

				String originBank = transactionInfo[0];
				String originAgency = transactionInfo[1];
				String originAccount = transactionInfo[2];
				String destinationBank = transactionInfo[3];
				String destinationAgency = transactionInfo[4];
				String destinationAccount = transactionInfo[5];
				String value = transactionInfo[6];
				LocalDateTime date = LocalDateTime.parse(transactionInfo[7]);
				
				BankAccount origin = new BankAccount(originBank, originAgency, originAccount);
				BankAccount destination = new BankAccount(destinationBank, destinationAgency, destinationAccount);

				list.add(new Transaction(origin, destination, value, date));
			} catch (DateTimeParseException | ArrayIndexOutOfBoundsException ignored) {
			}
		}
		scanner.close();
		return list;

	}
}
