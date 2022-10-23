package com.auth2.oseidclient.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;

@Service
@Transactional
public class SaveOseidUserDetailsService {

	public static final Logger LOGGER = LogManager.getLogger("SaveOseidUserDetailsService");

	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	SaveOseidUserDetailsService(OseidUserDetailsRepository oseidUserDetailsRepository){
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}

	public void saveUserDetails(OseidUserDetails newOseidUser) {
		LOGGER.info("loading new Oauth User: "+newOseidUser.getEmail()+", into database");
		oseidUserDetailsRepository.save(newOseidUser);	
	}
	
	
	
}
