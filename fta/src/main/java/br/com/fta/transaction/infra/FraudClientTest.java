package br.com.fta.transaction.infra;

import br.com.fta.model.Frauds;
import br.com.fta.transaction.domain.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Profile("test")
public class FraudClientTest implements FraudClient {

    public Frauds detectFrauds(List<Transaction> list) {
        return new Frauds(Set.of(), Set.of(), Set.of());
    }
}
