package com.leo.account.wallet.delegate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leo.account.wallet.dao.AccountDao;
import com.leo.account.wallet.dao.PlayerDao;
import com.leo.account.wallet.dao.TransactionDao;
import com.leo.account.wallet.dto.BalanceEnquiryResponse;
import com.leo.account.wallet.dto.GenericResponse;
import com.leo.account.wallet.dto.TransactionDto;
import com.leo.account.wallet.dto.TransactionHistoryResponse;
import com.leo.account.wallet.entity.Account;
import com.leo.account.wallet.entity.Player;
import com.leo.account.wallet.entity.Transaction;
import com.leo.account.wallet.exception.BusinessException;
import com.leo.account.wallet.type.TransactionType;

@Component
public class TransactionDelegate {
	public static final String PLAYER_OR_ACCOUNT_NOT_FOUND = "Player or account does not exist";

	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	PlayerDao playerDao;
	
	@Transactional
	public GenericResponse saveTransaction(TransactionDto transactionDto) throws BusinessException {
		Account account = accountDao.findByPlayerId(transactionDto.getPlayerId());
		TransactionType transactionType = TransactionType.getByCode(transactionDto.getTransactionType());	
		BigDecimal accountBalance = account.getBalance();
		BigDecimal transactionAmount = transactionDto.getTransactionAmount();
		validateTransaction(transactionDto, accountBalance, transactionType);	
			
		
		BigDecimal updatedBalance = transactionType == 
				TransactionType.DEBIT ? accountBalance.subtract(transactionAmount) : accountBalance.add(transactionAmount);
			
		Transaction transaction = new Transaction();
		transaction.setTransactionAmount(transactionDto.getTransactionAmount());
		transaction.setTransactionCode(transactionDto.getTransactionCode());
		transaction.setLatestBalance(updatedBalance);
		transaction.setTransactionType(transactionType.getCode());
		transaction.setAccount(account);
		transaction.setCreatedDate(LocalDateTime.now());		
		
		transactionDao.save(transaction);
		
		account.setBalance(updatedBalance);
		
		accountDao.save(account);
		
		GenericResponse saveTransactionResponse = new GenericResponse();
		saveTransactionResponse.setSuccess(Boolean.TRUE);
		return saveTransactionResponse;
			
	}	
	
	public BalanceEnquiryResponse getBalance(Long playerId) throws BusinessException {
		Account account = accountDao.findByPlayerId(playerId);
		
		if (account == null) {
			throw new BusinessException(PLAYER_OR_ACCOUNT_NOT_FOUND);
		}
		BalanceEnquiryResponse response = new BalanceEnquiryResponse();
		response.setBalance(account.getBalance());
		response.setPlayerId(playerId);
		response.setSuccess(Boolean.TRUE);
		return response;
		
	}
	
	public TransactionHistoryResponse getPlayerTransactions(Long playerId) throws BusinessException {
		Optional<Player> player = playerDao.findById(playerId);
		if (!player.isPresent()) {
			throw new BusinessException(PLAYER_OR_ACCOUNT_NOT_FOUND);
		}
		TransactionHistoryResponse response = new TransactionHistoryResponse();
		List<Transaction> transactions = transactionDao.findByPlayerId(playerId);
		List<TransactionDto> transactionsDto = transactions.stream().map(this :: map).collect(Collectors.toList());
		response.setTransactions(transactionsDto);
		response.setSuccess(Boolean.TRUE);
		return response;
		
	}
	
	private void validateTransaction(TransactionDto transactionDto, BigDecimal accountBalance, TransactionType transactionType) throws BusinessException {
		Transaction transaction = transactionDao.findByTransactionCode(transactionDto.getTransactionCode());
		if (transaction != null) {
			throw new BusinessException("Transaction should be unique, please provide a unique transaction code");
		}
		
		if (transactionType == TransactionType.DEBIT && accountBalance.subtract(transactionDto.getTransactionAmount()).compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("Balance cannot be negative after the transaction");
		}
	}
	
	private TransactionDto map(Transaction transaction) {
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionAmount(transaction.getTransactionAmount());
		transactionDto.setTransactionCode(transaction.getTransactionCode());
		transactionDto.setTransactionType(transaction.getTransactionType());
		transactionDto.setUpdatedBalance(transaction.getLatestBalance());
		transactionDto.setTransactionDate(transaction.getCreatedDate().toLocalDate());
		return transactionDto;
	}

}
