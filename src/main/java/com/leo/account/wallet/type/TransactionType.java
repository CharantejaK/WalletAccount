package com.leo.account.wallet.type;

public enum TransactionType {
	DEBIT("debit", "debit transactions"), CREDIT("credit", "credit transactions");

	private TransactionType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static TransactionType getByCode(String code) {
		for (TransactionType transactionType : TransactionType.values()) {
			if (transactionType.code.equals(code)) {
				return transactionType;
			}
		}

		return null;
	}
}
