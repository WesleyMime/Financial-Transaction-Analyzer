package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.Frauds;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("fraud-service")
public interface FraudClient {

    @PostMapping("/detect-frauds")
    public Frauds detectFrauds(List<Transaction> list);
}
