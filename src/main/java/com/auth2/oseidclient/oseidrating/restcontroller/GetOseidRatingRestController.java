package com.auth2.oseidclient.oseidrating.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.FindOseidRatingByIdService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class GetOseidRatingRestController {

	private static final Logger LOGGER = LogManager.getLogger("GetOseidRatingRestController");

	@Autowired
	private FindOseidRatingByIdService findOseidRatingByIdService;
	
	GetOseidRatingRestController(FindOseidRatingByIdService findOseidRatingByIdService
			){
		this.findOseidRatingByIdService = findOseidRatingByIdService;
	}
	
	
	@GetMapping("/rating")//?id=<id>
	public ResponseEntity<OseidRating> getOseidRule(@RequestParam Integer id){
		LOGGER.always();
		
		if(findOseidRatingByIdService.findOseidRatingById(id).getId() == -1) {
			LOGGER.info("No registered rule with id: "+id);
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("Rule: "+id+", available");
			return ResponseEntity.ok(findOseidRatingByIdService.findOseidRatingById(id));
		}
		
	}
	
}
