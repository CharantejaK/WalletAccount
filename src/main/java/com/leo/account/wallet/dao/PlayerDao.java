package com.leo.account.wallet.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.leo.account.wallet.entity.Player;

/**
 * The Interface to perform the CRUD operations on the Player Table
 */
@Transactional
public interface PlayerDao extends CrudRepository<Player, Long>{

	  /**
     * Lookup of the Player based upon the playerId
     *
     * @param playerId unique identifier of the Player
     * @return Player based on the playerId
     */
	public Player findByPlayerId(Long playerId);
}
