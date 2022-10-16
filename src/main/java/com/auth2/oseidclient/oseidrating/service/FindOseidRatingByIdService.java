package com.auth2.oseidclient.oseidrating.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@Service
public class FindOseidRatingByIdService {

	private static final Logger LOGGER = LogManager.getLogger("FindOseidRatingByIdService");

	@Autowired
	private OseidRatingRepository oseidRatingRepository;

	FindOseidRatingByIdService(OseidRatingRepository oseidRatingRepository) {
		this.oseidRatingRepository = oseidRatingRepository;
	}

	public OseidRating findOseidRatingById(Integer id) {
		OseidRating resultBid = new OseidRating();
		LOGGER.info("search result: " + oseidRatingRepository.findAll());
		int count = 0;
		if (oseidRatingRepository.findAll().size() > 0) {
			for (OseidRating i : oseidRatingRepository.findAll()) {
				if (Objects.equals(id, i.getId())) {
					LOGGER.info("Rating with Id: " + id + ", found ");
					resultBid = i;
					count++;
				}
			}
		}
		if (count == 0) {
			LOGGER.info("No rating found with Id: " + id);
			resultBid.setId(-1);
			;
		}
		LOGGER.info(resultBid);
		return resultBid;
	}

}
