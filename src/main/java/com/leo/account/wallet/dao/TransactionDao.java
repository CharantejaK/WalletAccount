package com.leo.account.wallet.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.leo.account.wallet.entity.Transaction;

/**
 * The Interface to perform the CRUD operations on the Transaction Table
 */
@Transactional
public interface TransactionDao extends CrudRepository<Transaction, Long> {

	  /**
     * Lookup of all of the Transactions based on the playerId.
     *
     * @param playerId the players unique identifier
     * @return the List<Transaction> 
     */
	@Query("select transaction from Transaction transaction where transaction.account.player.playerId = ?1 order by createdDate desc")
	public List<Transaction> findByPlayerId(Long playerId);
	
	  /**
     * Save or Update Transaction
     *
     * @param transaction
     * @return the saved or updated Transaction 
     */
	@SuppressWarnings("unchecked")
	public Transaction save(Transaction transaction);
	
	  /**
     * Lookup of the Transaction based upon the transactionCode
     *
     * @param String transactionCode
     * @return Transaction based on the transactionCode
     */
	public Transaction findByTransactionCode(String transactionCode);
}
