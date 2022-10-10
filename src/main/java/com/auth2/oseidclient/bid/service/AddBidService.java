package com.auth2.oseidclient.bid.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@Service
@Transactional
public class AddBidService {

	private static final Logger LOGGER = LogManager.getLogger("AddBidService");

	@Autowired
	private BidRepository bidRepository;
	
	AddBidService(BidRepository bidRepository){
		this.bidRepository = bidRepository;
	}

	public void saveBid(Bid newBid) {
		LOGGER.info("loading bid: "+newBid+", into database");
		bidRepository.save(newBid);
		
	}
	
}
