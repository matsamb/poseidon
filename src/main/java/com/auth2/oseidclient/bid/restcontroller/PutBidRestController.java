package com.auth2.oseidclient.bid.restcontroller;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.BidDTO;
import com.auth2.oseidclient.bid.service.AddBidService;
import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.bid.service.FindBidByIdService;
import com.auth2.oseidclient.entity.Bid;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PutBidRestController {

	public static final Logger LOGGER = LogManager.getLogger("PutBidRestController");

	@Autowired
	private FindBidByIdService findBidByIdService;
	
	@Autowired
	private AddBidService addBidService;
	
	PutBidRestController(AddBidService addBidService
			,FindBidByIdService findBidByIdService
			){
		this.addBidService = addBidService;
		this.findBidByIdService = findBidByIdService;
	}
	
//	@PutMapping("/bid")//?bidListId=<bidListId>
	public ResponseEntity<BidDTO> updateBid(@RequestBody Optional<BidDTO> bidDtoOptional){
		
		if(bidDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();
		}else {
			BidDTO bidDto =  bidDtoOptional.get();
			Bid foundBid = findBidByIdService.findBidById(bidDto.getBidListId());
			LOGGER.info(foundBid);
			if(foundBid.getBidListId() == -1) {
				LOGGER.info("Bid not found");
				return ResponseEntity.notFound().build();
			}else { 
				LOGGER.info("Bid found");
				foundBid.setBidListId(bidDto.getBidListId());
				foundBid.setAccount(bidDto.getAccount());
				foundBid.setType(bidDto.getType());
				foundBid.setBidQuantity(bidDto.getBidQuantity());
				LOGGER.info("Bid with account: "+foundBid.getAccount()+", updated");
				
				addBidService.saveBid(foundBid);
				LOGGER.info("Bid with account: "+foundBid.getAccount()+", loaded into database");
				return ResponseEntity.ok(bidDto);
				
			}
			
			
		}
		
	}
}
