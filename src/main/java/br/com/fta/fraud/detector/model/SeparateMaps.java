package br.com.fta.fraud.detector.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class SeparateMaps<T> {

	private final Map<T, BigDecimal> deposits = new HashMap<>();
	private final Map<T, BigDecimal> transfers = new HashMap<>();

	void byTransferAndDeposits(BigDecimal value, T origin, T destination) {

		BigDecimal totalValueOrigin = transfers.putIfAbsent(origin, value);
		BigDecimal totalValueDestination = deposits.putIfAbsent(destination, value);
		if (totalValueOrigin != null) {
			BigDecimal oldValue = transfers.get(origin);
			transfers.put(origin, oldValue.add(value));
		}

		if (totalValueDestination != null) {
			BigDecimal oldValue = deposits.get(destination);
			deposits.put(destination, oldValue.add(value));
		}
	}

	public Map<T, BigDecimal> getDeposits() {
		return deposits;
	}

	public Map<T, BigDecimal> getTransfers() {
		return transfers;
	}
}
