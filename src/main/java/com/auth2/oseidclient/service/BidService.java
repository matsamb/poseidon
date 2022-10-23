package com.auth2.oseidclient.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@Service
public class BidService {

	private static final Logger LOGGER = LogManager.getLogger("GetBidRestController");

	@Autowired
	private BidRepository bidRepository;

	BidService(BidRepository bidRepository) {
		this.bidRepository = bidRepository;
	}
	
	public Integer saveBid(Bid newBid) {
		LOGGER.info("loading bid: "+bidRepository.saveAndFlush(newBid).getBidListId()+", into database");
		Integer returnedBidId = bidRepository.saveAndFlush(newBid).getBidListId();
		return returnedBidId;
		
	}
	
	public List<Bid> findBidByAccount(String account) {
		List<Bid> found = bidRepository.findAll();
		LOGGER.info("found: "+found);
		List<Bid> result = new ArrayList<>();
		int count = 0;
		if(found.size() > 0) {
			LOGGER.info("Found "+result.size()+" Bids");
			for(Bid d:found) {
				LOGGER.debug("Evaluating bid list");
				if(d.getAccount().contentEquals(account)) {
					result.add(d);
					count++;
					LOGGER.info(count+" Bid found for "+account+" and added to result list");

				}
			}
		}
		if(result.size() == 0) {
			LOGGER.info("NotRegistered default bid for "+account);
			Bid notRegistredAccount = new Bid("Not_Registered");
			result.add(notRegistredAccount);
		}
		
		return result;
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
	
	public void deleteBid(Bid bid) {
		LOGGER.info("Bid deleted: "+bid.getBidListId());
		bidRepository.delete(bid);
		
	}
	
}
