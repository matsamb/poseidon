package com.auth2.oseidclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.entity.Trade;

public interface TradeRepository extends JpaRepository<Trade, Integer> {

}
