package com.auth2.oseidclient.restcontroller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.service.OseidRatingService;

@RestController
@RolesAllowed({ "ADMIN", "USER" })
public class OseidRatingRestController {

	private static final Logger LOGGER = LogManager.getLogger("OseidRatingRestController");

	@Autowired 
	private OseidRatingService oseidRatingService;

	OseidRatingRestController(OseidRatingService oseidRatingService) {
		this.oseidRatingService = oseidRatingService;
	}

	@GetMapping("/rating") // ?id=<id>
	public ResponseEntity<OseidRating> getOseidRule(@RequestParam Integer id) {
		LOGGER.always();

		if (oseidRatingService.findOseidRatingById(id).getId() == -1) {
			LOGGER.info("No registered rule with id: " + id);
			return ResponseEntity.notFound().build();
		} else {
			LOGGER.info("Rule: " + id + ", available");
			return ResponseEntity.ok(oseidRatingService.findOseidRatingById(id));
		}
	}

	@PostMapping("/rating")
	public ResponseEntity<OseidRating> addBid(@RequestBody Optional<@Valid OseidRating> ratingOptional) {

		if (ratingOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
		} else {
			OseidRating ratingDto = ratingOptional.get();

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<OseidRating>> violations = validator.validate(ratingDto);
			LOGGER.info("VALIDATION" + validator.validate(ratingDto));
			LOGGER.info("VIOLATION " + violations.size());

			if (violations.size() > 0) {

				LOGGER.info("Bad request, constraint violations: " + violations);
				return ResponseEntity.badRequest().build();

			} else {
				LOGGER.info("Rating to save: " + ratingDto);

				Integer newRatingId = oseidRatingService.saveOseidRating(ratingDto);
				ratingDto.setId(newRatingId);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/rating")
						.buildAndExpand("?name=" + newRatingId).toUri();

				LOGGER.info("Rating: " + newRatingId + ", URI created and added to database");

				return ResponseEntity.created(location).body(ratingDto);
			}
		}
	}

	@PutMapping("/rating")
	public ResponseEntity<OseidRating> updateRating(@RequestParam Integer id,
			@RequestBody Optional<OseidRating> ratingOptional) {
		LOGGER.always();

		if (ratingOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();
		} else {

			OseidRating rating = ratingOptional.get();
			if (oseidRatingService.findOseidRatingById(rating.getId()).getId() == -1) {

				LOGGER.info("No rating match for id: " + rating.getId());
				return ResponseEntity.notFound().build();
			} else {

				LOGGER.always();

				LOGGER.info("field to update: " + rating);
				Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
				Set<ConstraintViolation<OseidRating>> violations = validator.validate(rating);

				if (violations.size() > 0) {
					LOGGER.info("Bad request, constraint violations: " + violations);
					return ResponseEntity.badRequest().build();
				} else {
					LOGGER.info("updating rating " + rating.getId());
					/*
					 * OseidRating ratingToAlter = new OseidRating();
					 * 
					 * ratingToAlter.setId(rating.getId());
					 * ratingToAlter.setMoodysRating(rating.getMoodysRating());
					 * ratingToAlter.setFitchRating(rating.getFitchRating());
					 * ratingToAlter.setOrderNumber(rating.getOrderNumber());
					 * ratingToAlter.setSandPRating(rating.getSandPRating());
					 */

					oseidRatingService.saveOseidRating(rating);

					return ResponseEntity.ok(rating);
				}
			}
		}

	}

	@DeleteMapping("/rating") // ?id=<id>
	public ResponseEntity<OseidRating> deleteBid(@RequestParam Integer id) {

		/*
		 * if(findOseidRatingByIdService.findOseidRatingById(id).getId() == -1) {
		 * LOGGER.info("No registered rating with Id: "+id); return
		 * ResponseEntity.notFound().build(); }else {
		 */
		LOGGER.info("Found rating with Id: " + id);
		oseidRatingService.deleteOseidRating(oseidRatingService.findOseidRatingById(id));
		LOGGER.info("Rating deleted");
		return ResponseEntity.ok(new OseidRating());
		// }
	}

}
