package com.leo.account.wallet.dao;

import org.springframework.data.repository.CrudRepository;

import com.leo.account.wallet.entity.Player;

public interface PlayerDao extends CrudRepository<Player, Long>{

	public Player findByPlayerId(Long playerId);
}
