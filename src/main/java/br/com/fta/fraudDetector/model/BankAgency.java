package br.com.fta.fraudDetector.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import br.com.fta.transaction.domain.BankAccount;

public class BankAgency {

	@NotBlank
	private String bank;
	@NotBlank
	private String agency;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(agency, bank);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAgency other = (BankAgency) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(bank, other.bank);
	}
}
