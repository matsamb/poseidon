package com.auth2.oseidclient.oseidrating.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.DeleteOseidRatingService;
import com.auth2.oseidclient.oseidrating.service.FindOseidRatingByIdService;

@RestController
public class DeleteOseidRatingRestController {

public static final Logger LOGGER = LogManager.getLogger("DeleteOseidRatingRestController");
	
	@Autowired
	private DeleteOseidRatingService deleteOseidRatingService;
	
	@Autowired
	private FindOseidRatingByIdService findOseidRatingByIdService;
	
	DeleteOseidRatingRestController(DeleteOseidRatingService deleteOseidRatingService
		, FindOseidRatingByIdService findOseidRatingByIdService	
			){
		this.deleteOseidRatingService = deleteOseidRatingService;
		this.findOseidRatingByIdService = findOseidRatingByIdService;
	}
	
	@DeleteMapping("/rating")//?id=<id>
	public ResponseEntity<OseidRating> deleteBid(@RequestParam Integer id){

/*		if(findOseidRatingByIdService.findOseidRatingById(id).getId() == -1) {
			LOGGER.info("No registered rating with Id: "+id);
			return ResponseEntity.notFound().build();
		}else {*/
			LOGGER.info("Found rating with Id: "+id);
			deleteOseidRatingService.deleteOseidRating(findOseidRatingByIdService.findOseidRatingById(id));		
			LOGGER.info("Rating deleted");
			return ResponseEntity.ok(new OseidRating());
		//}
		
		
		
	}
	
}
