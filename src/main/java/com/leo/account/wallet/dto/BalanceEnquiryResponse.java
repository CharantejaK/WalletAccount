package com.leo.account.wallet.dto;

import java.math.BigDecimal;

public class BalanceEnquiryResponse extends GenericResponse {
	private BigDecimal balance;
	private Long playerId;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
}
