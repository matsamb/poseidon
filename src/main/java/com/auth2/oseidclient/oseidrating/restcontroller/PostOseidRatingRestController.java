package com.auth2.oseidclient.oseidrating.restcontroller;

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
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.AddOseidRatingService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PostOseidRatingRestController {

	private static final Logger LOGGER = LogManager.getLogger("PostOseidRatingRestController");

	@Autowired
	private AddOseidRatingService addOseidRatingService;
	
	PostOseidRatingRestController(
			 AddOseidRatingService addOseidRatingService
			){
		this.addOseidRatingService = addOseidRatingService;
	}
	
	
	@PostMapping("/rating")
	public ResponseEntity<OseidRating> addBid(@RequestBody Optional<@Valid OseidRating> ratingOptional){
		
		if(ratingOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
		}else {
			OseidRating ratingDto = ratingOptional.get();
	
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();	
			Set<ConstraintViolation<OseidRating>> violations = validator.validate(ratingDto);
			LOGGER.info("VALIDATION"+validator.validate(ratingDto));
			LOGGER.info("VIOLATION "+violations.size());

			if(violations.size()>0) {
				
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();

			}else {
				LOGGER.info("Rating to save: "+ratingDto);
				
				Integer newRatingId  = addOseidRatingService.saveOseidRating(ratingDto);
				ratingDto.setId(newRatingId);
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/rating")
						.buildAndExpand("?name="+newRatingId).toUri();
				
				LOGGER.info("Rating: "+newRatingId+", URI created and added to database");
				
				return ResponseEntity.created(location).body(ratingDto);
		}
	}
	}
}
