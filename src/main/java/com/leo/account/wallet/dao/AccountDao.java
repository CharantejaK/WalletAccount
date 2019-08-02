package com.leo.account.wallet.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.leo.account.wallet.entity.Account;

/**
 * The Interface to perform the CRUD operations on the Account Table
 */
@Transactional
public interface AccountDao extends CrudRepository<Account, Long> {

	  /**
     * Lookup of the Account based on the playerId.
     *
     * @param playerId the players unique identifier
     * @return the Account details
     */
	@Query("select account from Account account where account.player.playerId = ?1")
	public Account findByPlayerId(Long playerId);
	
	  /**
     * Save or Update the Account details
     *
     * @param account Account
     * @return returns the saved or updated Account details
     */
	@SuppressWarnings("unchecked")
	public Account save(Account account);
}
