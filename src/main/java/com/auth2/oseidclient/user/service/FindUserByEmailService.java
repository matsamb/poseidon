package com.auth2.oseidclient.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;

@Service
@Transactional
public class FindUserByEmailService {

	public static final Logger LOGGER = LogManager.getLogger("FindUserByEmailService");

	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;

	FindUserByEmailService(OseidUserDetailsRepository oseidUserDetailsRepository) {
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}

	public OseidUserDetails findUserByEmail(String email) {

		OseidUserDetails foundUser = new OseidUserDetails();
		foundUser.setLocked(false);
		LOGGER.info(oseidUserDetailsRepository.findByEmail(email).isPresent());
		if (oseidUserDetailsRepository.findByEmail(email).isPresent()) {
			foundUser = oseidUserDetailsRepository.findByEmail(email).get();
			LOGGER.info("User registered: "+foundUser.getEmail());
		}else {
			LOGGER.info("User not regidtred");
			foundUser.setEmail("Not_Registered");
		}
		LOGGER.info(foundUser);
		return foundUser;
	}

}
