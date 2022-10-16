package com.auth2.oseidclient.oseidrating.restcontroller;

import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.RuleDTO;
import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.AddOseidRatingService;
import com.auth2.oseidclient.oseidrating.service.FindOseidRatingByIdService;

@RestController
@RolesAllowed({"USER","ADMIN"})
public class PutOseidRatingRestController {

	private static final Logger LOGGER = LogManager.getLogger("PutOseidRatingRestController");

	@Autowired
	private FindOseidRatingByIdService findOseidRatingByIdService;
	
	@Autowired
	private AddOseidRatingService addOseidRatingService;
	
	PutOseidRatingRestController(AddOseidRatingService addOseidRatingService
			, FindOseidRatingByIdService findOseidRatingByIdService
			){
		this.addOseidRatingService = addOseidRatingService;
		this.findOseidRatingByIdService = findOseidRatingByIdService;		
	}
	
	@PutMapping("/rating")
	public ResponseEntity<OseidRating> updateRating(@RequestParam Integer id, @RequestBody Optional<OseidRating> ratingOptional){
		LOGGER.always();
		
		if(ratingOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();
		}else {
			
			OseidRating rating = ratingOptional.get();
			if (findOseidRatingByIdService.findOseidRatingById(rating.getId()).getId() == -1) {

				LOGGER.info("No rating match for id: "+rating.getId());
				return ResponseEntity.notFound().build();
			}else {
			
			LOGGER.always();
			
			LOGGER.info("field to update: "+rating);
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<OseidRating>> violations = validator.validate(rating);
			
			if(violations.size() > 0) {
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();
			}else {
				LOGGER.info("updating rating "+rating.getId());
/*				OseidRating ratingToAlter = new OseidRating();
				
				ratingToAlter.setId(rating.getId());
				ratingToAlter.setMoodysRating(rating.getMoodysRating());
				ratingToAlter.setFitchRating(rating.getFitchRating());
				ratingToAlter.setOrderNumber(rating.getOrderNumber());
				ratingToAlter.setSandPRating(rating.getSandPRating());*/

				addOseidRatingService.saveOseidRating(rating);
				
				return ResponseEntity.ok(rating);
			}
			}	
		}
		
	}
	
}
