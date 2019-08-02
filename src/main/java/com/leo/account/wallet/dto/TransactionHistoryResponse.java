package com.leo.account.wallet.dto;

import java.util.List;

public class TransactionHistoryResponse extends GenericResponse {
	private List<TransactionDto> transactions;

	public List<TransactionDto> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDto> transactions) {
		this.transactions = transactions;
	}
	
}
