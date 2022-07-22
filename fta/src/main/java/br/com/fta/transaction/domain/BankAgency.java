package br.com.fta.transaction.domain;

public class BankAgency {

    private String bank;
    private String agency;

    public BankAgency() {}

    public BankAgency(BankAccount account) {
        this.bank = account.getBank();
        this.agency = account.getAgency();
    }

    public String getBank() {
        return bank;
    }

    public String getAgency() {
        return agency;
    }
}

