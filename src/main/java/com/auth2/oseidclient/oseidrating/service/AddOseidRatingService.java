package com.auth2.oseidclient.oseidrating.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@Service
public class AddOseidRatingService {

	private static final Logger LOGGER = LogManager.getLogger("AddOseidRatingService");

	@Autowired
	private OseidRatingRepository oseidRatingRepository;
	
	AddOseidRatingService(OseidRatingRepository oseidRatingRepository){
		this.oseidRatingRepository = oseidRatingRepository;
	}

	public Integer saveOseidRating(OseidRating ratingDto) {
		LOGGER.info("SAVE AND FLUSH: "+oseidRatingRepository.saveAndFlush(ratingDto));
		return oseidRatingRepository.saveAndFlush(ratingDto).getId();
		
	}
	
	
	
}
