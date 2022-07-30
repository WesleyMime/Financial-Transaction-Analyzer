package br.com.fta.transaction.application;

import br.com.fta.model.Frauds;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@FeignClient(value = "fraud-service", fallback = FraudClient.FraudClientFallback.class)
@Profile({"dev", "default", "prod"})
public interface FraudClient {

    @PostMapping("/detect-frauds")
    Frauds detectFrauds(List<Transaction> list);

    @Component
    @Profile({"dev", "default", "prod"})
    class FraudClientFallback implements FraudClient {

        @Override
        public Frauds detectFrauds(List<Transaction> list) {
            return new Frauds(List.of(), Set.of(), Set.of());
        }
    }
}
