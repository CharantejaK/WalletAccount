package com.leo.account.wallet.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.leo.account.wallet.entity.Transaction;

@Transactional
public interface TransactionDao extends CrudRepository<Transaction, Long> {

	@Query("select transaction from Transaction transaction where transaction.account.player.playerId = ?1 order by createdDate desc")
	public List<Transaction> findByPlayerId(Long playerId);
	
	@SuppressWarnings("unchecked")
	public Transaction save(Transaction transaction);
	
	public Transaction findByTransactionCode(String transactionCode);
}
