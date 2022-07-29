package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.Frauds;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("fraud-service")
@Profile({"dev", "default", "prod"})
public interface FraudClient {

    @PostMapping("/detect-frauds")
    public Frauds detectFrauds(List<Transaction> list);
}
