package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("transactions-generator-service")
public interface GeneratorClient {

    @GetMapping("/generate-transactions")
    List<Transaction> generateTransactions();

}
