package com.leo.account.wallet.delegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.leo.account.wallet.dao.AccountDao;
import com.leo.account.wallet.dto.BalanceEnquiryResponse;
import com.leo.account.wallet.entity.Account;
import com.leo.account.wallet.exception.BusinessException;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TransactionDelegateTest {
	
	public static final Long PLAYER_ID = 1346L;
	public static final BigDecimal BALANCE = new BigDecimal(10);
	
	@InjectMocks
	private TransactionDelegate transactionDelegate;
	
	@Mock
	AccountDao accountDao;	 
	

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
}
