package com.auth2.oseidclient.oseidrating.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.oseidrating.service.SaveOseidRatingService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PostOseidRatingRestController {

	private static final Logger LOGGER = LogManager.getLogger("OseidRatingRestController");
	
	@Autowired
	private SaveOseidRatingService saveOseidRatingService;
	
	PostOseidRatingRestController(SaveOseidRatingService saveOseidRatingService
			){
		this.saveOseidRatingService = saveOseidRatingService;
	}
	
}
