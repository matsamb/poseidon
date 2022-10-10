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
public class DeleteBidService {

	public static final Logger LOGGER = LogManager.getLogger("DeleteBidService");

	@Autowired
	private BidRepository bidRepository;
	
	DeleteBidService(BidRepository bidRepository){
		this.bidRepository = bidRepository;
	}

	public void deleteBid(Bid bid) {
		LOGGER.info("Bid deleted: "+bid.getBidListId());
		bidRepository.delete(bid);
		
	}
	
	
	
}
