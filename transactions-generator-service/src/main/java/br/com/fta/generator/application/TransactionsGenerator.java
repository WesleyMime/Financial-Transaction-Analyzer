package br.com.fta.generator.application;

import br.com.fta.generator.model.BankAccount;
import br.com.fta.generator.model.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionsGenerator {

	public static List<Transaction> generate() {
		List<Transaction> transactions = new ArrayList<>();
		int year = randomValueInt(1990, LocalDate.now().getYear());
		int month = randomValueInt(1, 13);
		int day = randomDay(year, month);
		LocalDate dateOfTransactions = LocalDate.of(year, month, day);
		
		while (transactions.size() < 4000) {
			BankAccount origin = randomAccount();
			BankAccount destination = randomAccount();
			int valueInt = randomValueInt(0, 50000);
			LocalDateTime date = randomDateTime(dateOfTransactions); 
			
			// Adds possibility to detect fraud agency.
			if (date.getMinute() == 0) {
				valueInt += 265746329;
			}
			// Adds possibility to detect fraud account.
			if (date.getMinute() == 1) {
				valueInt += 746329;
			}
			String value = String.valueOf(valueInt);
			
			Transaction transaction = new Transaction(origin, destination, value, date);
			transactions.add(transaction);
		}
		return transactions;
	}

	private static String randomString() {
        String letters = "ABC";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < 3) { // length of the random string.
            int index = (int) (rnd.nextFloat() * letters.length());
            builder.append(letters.charAt(index));
        }
		return builder.toString();
    }

	private static BankAccount randomAccount() {
		String bank = randomString();
		String agency = randomValueString(0, 3);
		String account = randomValueString(0, 3);
		return new BankAccount(bank, agency, account);
	}


	private static String randomValueString(int min, int max) {
		return String.valueOf(randomValueInt(min, max));
	}
	
	private static int randomValueInt(int min, int max) {
		return new Random().nextInt(min, max);
	}
	
	private static int randomDay(int year, int month) {
		int maxDays = LocalDate.of(year, month, 1).lengthOfMonth();
		return randomValueInt(1, maxDays);
	}

	private static LocalDateTime randomDateTime(LocalDate date) {
		int hour = randomValueInt(0, 24);
		int min = randomValueInt(0, 60);
		
		return LocalDateTime.of(date, LocalTime.of(hour, min));
	}

}
