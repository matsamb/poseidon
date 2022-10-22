package com.auth2.oseidclient.restcontroller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.entity.Bid;

@RestController
public class BidRestController {

	private static final Logger LOGGER = LogManager.getLogger("GetBidRestController");

	@Autowired
	private FindBidByAccountService findBidByAccountService;

	BidRestController(FindBidByAccountService findBidByAccountService
			
			){
		this.findBidByAccountService = findBidByAccountService;
	}
	
	@GetMapping("/bid") // ?account=<account>
	public Iterable<Bid> findBidByAccount(@RequestParam String account) {
		
		List<Bid> result = findBidByAccountService.findBidByAccount(account);
		LOGGER.info(result);
		
		if (result.get(0).getAccount() != "Not_Registered") {
			LOGGER.info("Bid displayed for " + account);
		} else {
			LOGGER.info("No registered bid for " + account);
		}

		return result;
	}
	
}
