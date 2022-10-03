package com.auth2.oseidclient.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.bid.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	
}
