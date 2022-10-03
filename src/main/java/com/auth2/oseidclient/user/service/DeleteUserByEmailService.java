package com.auth2.oseidclient.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.user.repository.OseidUserDetailsRepository;

@Service
public class DeleteUserByEmailService {

	public static final Logger LOGGER = LogManager.getLogger("DeleteUserByEmailService");
	
	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	DeleteUserByEmailService(OseidUserDetailsRepository oseidUserDetailsRepository){
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}

	public void deleteUserByEmail(String email) {
		LOGGER.info("User: "+email+", deleted");
		oseidUserDetailsRepository.deleteById(email);
		
	}
	
	
	
}
