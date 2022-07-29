package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("transactions-generator-service")
@Profile({"dev", "default", "prod"})
public interface GeneratorClient {

    @GetMapping("/generate-transactions")
    List<Transaction> generateTransactions();

}
