package com.leo.account.wallet.delegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.leo.account.wallet.dao.AccountDao;
import com.leo.account.wallet.dao.TransactionDao;
import com.leo.account.wallet.dto.BalanceEnquiryResponse;
import com.leo.account.wallet.dto.TransactionDto;
import com.leo.account.wallet.entity.Account;
import com.leo.account.wallet.entity.Transaction;
import com.leo.account.wallet.exception.BusinessException;
import com.leo.account.wallet.exception.DuplicateTransactionCodeException;
import com.leo.account.wallet.exception.InsufficientFundsException;
import com.leo.account.wallet.type.TransactionType;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDelegateTest {

	public static final Long PLAYER_ID = 1346L;
	public static final BigDecimal BALANCE = new BigDecimal(10);
	public static final BigDecimal TRANSACTION_AMOUNT = new BigDecimal(11);
	public static final String TRANSACTION_CODE = "TRAN132";

	@InjectMocks
	private TransactionDelegate transactionDelegate;

	@Mock
	AccountDao accountDao;

	@Mock
	TransactionDao transactionDao;

	@Test
	public void testGetBalance() throws BusinessException {
		Account account = new Account();
		account.setBalance(BALANCE);
		when(accountDao.findByPlayerId(PLAYER_ID)).thenReturn(account);
		BalanceEnquiryResponse response = transactionDelegate.getBalance(PLAYER_ID);
		assertEquals(BALANCE, response.getBalance());
		assertEquals(Boolean.TRUE, response.getSuccess());
	}

	@Test(expected = BusinessException.class)
	public void testBalancePlayerNotFound() throws BusinessException {
		when(accountDao.findByPlayerId(PLAYER_ID)).thenReturn(null);
		transactionDelegate.getBalance(PLAYER_ID);
	}

	@Test(expected = InsufficientFundsException.class)
	public void insufficientFunds() throws BusinessException {
		Account account = new Account();
		account.setBalance(BALANCE);
		when(accountDao.findByPlayerId(PLAYER_ID)).thenReturn(account);
		when(transactionDao.findByTransactionCode(TRANSACTION_CODE)).thenReturn(null);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionType(TransactionType.DEBIT.getCode());
		transactionDto.setTransactionAmount(TRANSACTION_AMOUNT);
		transactionDto.setPlayerId(PLAYER_ID);
		transactionDto.setTransactionCode(TRANSACTION_CODE);

		transactionDelegate.saveTransaction(transactionDto);
	}

	@Test(expected = DuplicateTransactionCodeException.class)
	public void duplicateTransactionCode() throws BusinessException {
		Account account = new Account();
		account.setBalance(BALANCE);
		when(accountDao.findByPlayerId(PLAYER_ID)).thenReturn(account);
		when(transactionDao.findByTransactionCode(TRANSACTION_CODE)).thenReturn(new Transaction());

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionType(TransactionType.CREDIT.getCode());
		transactionDto.setTransactionAmount(TRANSACTION_AMOUNT);
		transactionDto.setPlayerId(PLAYER_ID);
		transactionDto.setTransactionCode(TRANSACTION_CODE);

		transactionDelegate.saveTransaction(transactionDto);
	}

	@Test
	public void successfulTransaction() throws BusinessException {
		Account account = new Account();
		account.setBalance(BALANCE);
		when(accountDao.findByPlayerId(PLAYER_ID)).thenReturn(account);
		when(transactionDao.findByTransactionCode(TRANSACTION_CODE)).thenReturn(null);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionType(TransactionType.CREDIT.getCode());
		transactionDto.setTransactionAmount(TRANSACTION_AMOUNT);
		transactionDto.setPlayerId(PLAYER_ID);
		transactionDto.setTransactionCode(TRANSACTION_CODE);

		transactionDelegate.saveTransaction(transactionDto);

		BigDecimal updatedBalance = BALANCE.add(TRANSACTION_AMOUNT);
		assertEquals(account.getBalance(), updatedBalance);
		verify(accountDao).save(account);

	}

}
