package br.com.fta.transaction.application;

import br.com.fta.transaction.domain.Frauds;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Profile("test")
public class FraudClientTest implements FraudClient {

    public Frauds detectFrauds(List<Transaction> list) {
        return new Frauds(List.of(), Set.of(), Set.of());
    };
}
