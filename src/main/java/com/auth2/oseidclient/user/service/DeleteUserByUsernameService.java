package com.auth2.oseidclient.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.repository.OseidUserDetailsRepository;

@Service
@Transactional
public class DeleteUserByUsernameService {

	public static final Logger LOGGER = LogManager.getLogger("DeleteUserByUsernameService");
	
	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	DeleteUserByUsernameService(OseidUserDetailsRepository oseidUserDetailsRepository){
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}

	public void deleteUserByUsername(String username) {
		LOGGER.info("User: "+username+", deleted");
		oseidUserDetailsRepository.deleteById(username);
		
	}
	
	
	
}
