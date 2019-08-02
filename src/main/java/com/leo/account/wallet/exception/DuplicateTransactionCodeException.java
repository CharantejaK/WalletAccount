package com.leo.account.wallet.exception;

public class DuplicateTransactionCodeException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	public DuplicateTransactionCodeException() {
		super("Transaction should be unique, please provide a unique transaction code");
	}

	public DuplicateTransactionCodeException(Exception e) {
		super(e);
	}

}
