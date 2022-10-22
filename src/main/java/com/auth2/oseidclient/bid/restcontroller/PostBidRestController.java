package com.auth2.oseidclient.bid.restcontroller;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.DTO.BidDTO;
import com.auth2.oseidclient.bid.service.AddBidService;
import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.entity.Bid;

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
	
//	@PostMapping("/bid")
	public ResponseEntity<Bid> addBid(@RequestBody Optional<@Valid BidDTO> bidDtoOptional){
		
		if(bidDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
		}else {
			BidDTO bidDto = bidDtoOptional.get();
			LOGGER.info("First bid account: "+findBidByAccountService.findBidByAccount(bidDto.getAccount()).get(0).getAccount());
	
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();	
			Set<ConstraintViolation<BidDTO>> violations = validator.validate(bidDto);
			LOGGER.info("VALIDATION"+validator.validate(bidDto));
			LOGGER.info("VIOLATION "+violations.size());

			if(violations.size()>0) {
				
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();

			}else {
			
				Bid newBid = new Bid();
				newBid.setAccount(bidDto.getAccount());
				newBid.setBidQuantity(bidDto.getBidQuantity());
				newBid.setType(bidDto.getType());
				
				LOGGER.info("Bid: "+newBid.getAccount()+", URI created and added to database");
				Integer savedBid = addBidService.saveBid(newBid);
				newBid.setBidListId(savedBid);
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/bid")
						.buildAndExpand("?account="+savedBid).toUri();
				
				return ResponseEntity.created(location).body(newBid);

		}
	}
	}
	
}
