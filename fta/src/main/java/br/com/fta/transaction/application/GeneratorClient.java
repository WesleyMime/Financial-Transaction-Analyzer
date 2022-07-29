package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.BankAccount;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(value = "transactions-generator-service", fallback = GeneratorClient.GeneratorClientFallback.class)
@Profile({"dev", "default", "prod"})
public interface GeneratorClient {

    @GetMapping("/generate-transactions")
    List<Transaction> generateTransactions();


    @Component
    @Profile({"dev", "default", "prod"})
    class GeneratorClientFallback implements GeneratorClient {

        @Override
        public List<Transaction> generateTransactions() {
            return List.of(new Transaction(
                    new BankAccount("Foo", "Bar", "123"),
                    new BankAccount("Bar", "Foo", "456"),
                    "10523", LocalDateTime.now()
            ));
        }
    }

}
