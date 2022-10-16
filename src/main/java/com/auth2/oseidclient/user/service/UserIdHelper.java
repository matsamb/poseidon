package com.auth2.oseidclient.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.UserId;
import com.auth2.oseidclient.repository.UserIdRepository;

@Service
public class UserIdHelper {

	public static final Logger LOGGER = LogManager.getLogger("UserIdHelper");

	@Autowired
	private UserIdRepository userIdRepository;
	
	UserIdHelper(UserIdRepository userIdRepository){
		this.userIdRepository = userIdRepository;
	}
	
	public UserId createUserId() {
		
		UserId userId = userIdRepository.saveAndFlush(new UserId());
		LOGGER.info("Created Id: "+userId.getId());		
		return userId;
				
		
	}
	
}
