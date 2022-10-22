package com.auth2.oseidclient.restcontroller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.DTO.BidDTO;
import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.service.BidService;

@RestController
public class BidRestController {

	private static final Logger LOGGER = LogManager.getLogger("GetBidRestController");

	@Autowired
	private BidService bidService;

	BidRestController(BidService bidService

	) {
		this.bidService = bidService;
	}

	@GetMapping("/bid") // ?account=<account>
	public Iterable<Bid> findBidByAccount(@RequestParam String account) {

		List<Bid> result = bidService.findBidByAccount(account);
		LOGGER.info(result);

		if (result.get(0).getAccount() != "Not_Registered") {
			LOGGER.info("Bid displayed for " + account);
		} else {
			LOGGER.info("No registered bid for " + account);
		}

		return result;
	}

	@PostMapping("/bid")
	public ResponseEntity<Bid> addBid(@RequestBody Optional<@Valid BidDTO> bidDtoOptional) {

		if (bidDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
		} else {
			BidDTO bidDto = bidDtoOptional.get();
			LOGGER.info("First bid account: " + bidService.findBidByAccount(bidDto.getAccount()).get(0).getAccount());

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<BidDTO>> violations = validator.validate(bidDto);
			LOGGER.info("VALIDATION" + validator.validate(bidDto));
			LOGGER.info("VIOLATION " + violations.size());

			if (violations.size() > 0) {

				LOGGER.info("Bad request, constraint violations: " + violations);
				return ResponseEntity.badRequest().build();

			} else {

				Bid newBid = new Bid();
				newBid.setAccount(bidDto.getAccount());
				newBid.setBidQuantity(bidDto.getBidQuantity());
				newBid.setType(bidDto.getType());

				LOGGER.info("Bid: " + newBid.getAccount() + ", URI created and added to database");
				Integer savedBid = bidService.saveBid(newBid);
				newBid.setBidListId(savedBid);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/bid")
						.buildAndExpand("?account=" + savedBid).toUri();

				return ResponseEntity.created(location).body(newBid);

			}
		}
	}

	@DeleteMapping("/bid") // ?bidlistid=<bidListId>
	public ResponseEntity<BidDTO> deleteBid(@RequestParam Integer bidlistid) {

		if (bidService.findBidById(bidlistid).getBidListId() == -1) {
			LOGGER.info("No registered bid with Id: " + bidlistid);
			return ResponseEntity.notFound().build();
		} else {
			LOGGER.info("Found bid with Id: " + bidlistid);
			bidService.deleteBid(bidService.findBidById(bidlistid));
			LOGGER.info("Bid deleted");
			return ResponseEntity.ok(new BidDTO());
		}

	}

}
