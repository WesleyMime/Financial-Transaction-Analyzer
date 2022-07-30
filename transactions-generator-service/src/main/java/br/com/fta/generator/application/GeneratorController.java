package br.com.fta.generator.application;

import br.com.fta.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneratorController{

    @GetMapping("/generate-transactions")
    public List<Transaction> generateTransactions() {
        return TransactionsGenerator.generate();
    }
}
