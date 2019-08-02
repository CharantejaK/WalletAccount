package com.leo.account.wallet.dto;

import java.math.BigDecimal;

/**
 * A class representing the getBalance service response.
 */
public class BalanceEnquiryResponse extends GenericResponse {
	/**
	 * current balance of the Player
	 */
	private BigDecimal balance;
	
	/**
	 * unique identifier of the player
	 */
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
