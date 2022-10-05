package com.auth2.oseidclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth2.oseidclient.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	
}
