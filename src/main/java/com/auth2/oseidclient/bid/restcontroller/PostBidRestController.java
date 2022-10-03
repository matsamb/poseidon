package com.auth2.oseidclient.bid.restcontroller;

import java.net.URI;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.bid.entity.Bid;
import com.auth2.oseidclient.bid.service.AddBidService;
import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.user.DTO.BidDTO;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PostBidRestController {

	private static final Logger LOGGER = LogManager.getLogger("PostBidRestController");
	
	@Autowired
	private AddBidService addBidService;
	
	@Autowired
	private FindBidByAccountService findBidByAccountService;
	
	PostBidRestController(AddBidService addBidService
			,FindBidByAccountService findBidByAccountService
			){
		this.addBidService =addBidService;
		this.findBidByAccountService = findBidByAccountService;
	}
	
	@PostMapping("/bid")
	public ResponseEntity<BidDTO> addBid(@RequestBody Optional<BidDTO> bidDtoOptional){
		
		if(bidDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
		}else {
			BidDTO bidDto = bidDtoOptional.get();
			LOGGER.info("First bid account: "+findBidByAccountService.findBidByAccount(bidDto.getAccount()).get(0).getAccount());
			
	//		if(findBidByAccountService.findBidByAccount(bidDto.getAccount()).get(0).getAccount() == "Not_Registered") {
				Bid newBid = new Bid();
				newBid.setAccount(bidDto.getAccount());
				newBid.setBidQuantity(bidDto.getBidQuantity());
				newBid.setType(bidDto.getType());
				
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/Bid")
						.buildAndExpand("?account="+newBid.getAccount()).toUri();
				
				LOGGER.info("Bid: "+newBid.getAccount()+", URI created and added to database");
				addBidService.saveBid(newBid);
				return ResponseEntity.created(location).build();
	//		}else {
	//			LOGGER.info("bid all ready exists");
	// TODO: check for multiple bids list
		//		return ResponseEntity.ok(bidDto);
			//}
		}
		
	}
	
}
