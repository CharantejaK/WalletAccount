package com.leo.account.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A class representing transaction details.
 */
@JsonIgnoreProperties
public class TransactionDto {

	 /**
     * The primary identifier of the player.
     */
	private Long playerId;
	
	 /**
     * Unique identifier for a transaction
     */
	private String transactionCode;
	
	 /**
     * identifier whether a transaction is debit or credit
     */
	private String transactionType;
	
	 /**
     * amount for the transaction
     */
	private BigDecimal transactionAmount;
	
	 /**
     * updated amount after a transaction is sucessfull
     */
	private BigDecimal updatedBalance;
	
	 /**
     * date when the transaction is made
     */
	private LocalDate transactionDate;

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getUpdatedBalance() {
		return updatedBalance;
	}

	public void setUpdatedBalance(BigDecimal updatedBalance) {
		this.updatedBalance = updatedBalance;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}	
	
}
