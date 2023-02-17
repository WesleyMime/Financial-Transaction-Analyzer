package br.com.fta.detector.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class DepositAndTransferValues<T> {
	
	private final Map<T, BigDecimal> deposits = new HashMap<>();
	private final Map<T, BigDecimal> transfers = new HashMap<>();

	void add(BigDecimal value, T origin, T destination) {
		addToDeposits(value, destination);
		addToTransfers(value, origin);
	}

	private void addToDeposits(BigDecimal value, T destination) {
		BigDecimal totalValueDestination = deposits.putIfAbsent(destination, value);
		if (totalValueDestination != null) {
			BigDecimal oldValue = deposits.get(destination);
			deposits.put(destination, oldValue.add(value));
		}
	}

	private void addToTransfers(BigDecimal value, T origin) {
		BigDecimal totalValueOrigin = transfers.putIfAbsent(origin, value);
		if (totalValueOrigin != null) {
			BigDecimal oldValue = transfers.get(origin);
			transfers.put(origin, oldValue.add(value));
		}
	}

	public Map<T, BigDecimal> getDeposits() {
		return deposits;
	}

	public Map<T, BigDecimal> getTransfers() {
		return transfers;
	}
}
