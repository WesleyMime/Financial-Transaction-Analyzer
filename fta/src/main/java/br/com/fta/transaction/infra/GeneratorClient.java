package br.com.fta.transaction.infra;

import br.com.fta.shared.infra.FeignConfiguration;
import br.com.fta.transaction.domain.Transaction;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Profile({"prod", "dev"})
@FeignClient(value = "transactions-generator-service",
        configuration = FeignConfiguration.class)
public interface GeneratorClient {

    @GetMapping("/generate-transactions")
    @Retryable(include = {FeignException.class}, maxAttempts = 5)
    List<Transaction> generateTransactions();
}
