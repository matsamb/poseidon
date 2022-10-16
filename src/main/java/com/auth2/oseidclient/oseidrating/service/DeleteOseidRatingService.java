package com.auth2.oseidclient.oseidrating.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.DTO.BidDTO;
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.BidRepository;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@Service
@Transactional
public class DeleteOseidRatingService {

	public static final Logger LOGGER = LogManager.getLogger("DeleteOseidRatingService");

	@Autowired
	private OseidRatingRepository oseidRatingRepository;
	
	DeleteOseidRatingService(OseidRatingRepository oseidRatingRepository){
		this.oseidRatingRepository = oseidRatingRepository;
	}

	public void deleteOseidRating(OseidRating rating) {
		LOGGER.info("Rating deleted: "+rating.getId());
		oseidRatingRepository.delete(rating);

	}
	
}
