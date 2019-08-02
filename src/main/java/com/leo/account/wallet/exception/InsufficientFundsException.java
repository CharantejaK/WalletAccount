package com.leo.account.wallet.exception;

public class InsufficientFundsException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	public InsufficientFundsException() {
		super("Balance cannot be negative after the transaction");
	}

	public InsufficientFundsException(Exception e) {
		super(e);
	}

}
