package br.com.fta.fraud.detector.model;

import br.com.fta.transaction.domain.BankAccount;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class BankAgency {

	@NotBlank
	private final String bank;
	@NotBlank
	private final String agency;
	
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
