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
public class FindUserByUsernameService {

	public static final Logger LOGGER = LogManager.getLogger("FindUserByUsernameService");

	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;

	FindUserByUsernameService(OseidUserDetailsRepository oseidUserDetailsRepository) {
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}

	public OseidUserDetails findUserByUsername(String email) {

		OseidUserDetails foundUser = new OseidUserDetails();
		foundUser.setLocked(false);
		LOGGER.info(oseidUserDetailsRepository.findByUsername(email).isPresent());
		if (oseidUserDetailsRepository.findByUsername(email).isPresent()) {
			foundUser = oseidUserDetailsRepository.findByUsername(email).get();
			LOGGER.info("User registered: "+foundUser.getUsername());
		}else {
			LOGGER.info("User not regidtred");
			foundUser.setEmail("Not_Registered");
			foundUser.setUsername("Not_Registered");
		}
		LOGGER.info(foundUser);
		return foundUser;
	}

}
