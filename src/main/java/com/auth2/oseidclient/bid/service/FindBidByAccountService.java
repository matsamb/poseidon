package com.auth2.oseidclient.bid.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@Service
@Transactional
public class FindBidByAccountService {

	private static final Logger LOGGER = LogManager.getLogger("FindBidByAccountService");

	@Autowired
	private BidRepository bidRepository;

	FindBidByAccountService(BidRepository bidRepository) {
		this.bidRepository = bidRepository;
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

}
