package br.com.fta.detector.application;

import br.com.fta.detector.model.FraudDetector;
import br.com.fta.detector.model.Frauds;
import br.com.fta.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FraudController {

    @Autowired
    private FraudDetector fraudDetector;

    @PostMapping("/detect-frauds")
    public Frauds detectFrauds(@RequestBody List<Transaction> list) {
        return fraudDetector.analyzeTransactions(list);
    }
}
