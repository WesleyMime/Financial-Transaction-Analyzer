package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.BankAccount;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("test")
public class GeneratorClientTest implements GeneratorClient {

    public List<Transaction> generateTransactions() {
        return List.of(new Transaction(new BankAccount("Foo", "Bar", "123"),
            new BankAccount("Bar", "Foo", "456"), "100", LocalDateTime.now()));
    };

}
