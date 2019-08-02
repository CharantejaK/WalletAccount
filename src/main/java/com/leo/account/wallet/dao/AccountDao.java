package com.leo.account.wallet.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.leo.account.wallet.entity.Account;

@Transactional
public interface AccountDao extends CrudRepository<Account, Long> {

	@Query("select account from Account account where account.player.playerId = ?1")
	public Account findByPlayerId(Long playerId);
	
	@SuppressWarnings("unchecked")
	public Account save(Account account);
}
