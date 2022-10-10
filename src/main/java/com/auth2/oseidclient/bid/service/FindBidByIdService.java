package com.auth2.oseidclient.bid.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@Service
@Transactional
public class FindBidByIdService {

	public static final Logger LOGGER = LogManager.getLogger("PutBidRestController");

	@Autowired
	private BidRepository bidRepository;

	FindBidByIdService(BidRepository bidRepository) {
		this.bidRepository = bidRepository;
	}

	public Bid findBidById(Integer bidListId) {
		Bid resultBid = new Bid();
		LOGGER.info("search result: " + bidRepository.findAll());
		int count = 0;
		for (Bid i : bidRepository.findAll()) {
			if (Objects.equals(bidListId, i.getBidListId())) {
				LOGGER.info("Bid with Id: " + bidListId + ", found ");
				resultBid = i;
				count++;
			}
		}
		
		if(count == 0 ) {
			LOGGER.info("No Bid found with Id: " + bidListId);
			resultBid.setBidListId(-1);;
		}
		return resultBid;
	}

}
