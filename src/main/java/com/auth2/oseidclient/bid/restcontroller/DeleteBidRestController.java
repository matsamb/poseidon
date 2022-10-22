package com.auth2.oseidclient.bid.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.BidDTO;
import com.auth2.oseidclient.bid.service.DeleteBidService;
import com.auth2.oseidclient.bid.service.FindBidByIdService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class DeleteBidRestController {

	public static final Logger LOGGER = LogManager.getLogger("DeleteBidRestController");
	
	@Autowired
	private DeleteBidService deleteBidService;
	
	@Autowired
	private FindBidByIdService findBidByIdService;
	
	DeleteBidRestController(DeleteBidService deleteBidService
		, FindBidByIdService findBidByIdService	
			){
		this.deleteBidService = deleteBidService;
		this.findBidByIdService = findBidByIdService;
	}
	
	
	
	@DeleteMapping("/bid")//?bidlistid=<bidListId>
	public ResponseEntity<BidDTO> deleteBid(@RequestParam Integer bidlistid){

		if(findBidByIdService.findBidById(bidlistid).getBidListId() == -1) {
			LOGGER.info("No registered bid with Id: "+bidlistid);
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("Found bid with Id: "+bidlistid);
			deleteBidService.deleteBid(findBidByIdService.findBidById(bidlistid));		
			LOGGER.info("Bid deleted");
			return ResponseEntity.ok(new BidDTO());
		}
		
		
		
	}
	
	
}
