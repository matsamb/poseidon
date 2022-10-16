package com.auth2.oseidclient.user.service;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.entity.UserId;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;

@Service
public class FindUserByUserIdService {

	private static final Logger LOGGER = LogManager.getLogger("FindUserByUserIdService");
	
	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	FindUserByUserIdService(OseidUserDetailsRepository oseidUserDetailsRepository
			){
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}
	
	public OseidUserDetails findUserByUserId(Integer id) {
		
		LOGGER.always();
		UserId uid = new UserId(-1);
		OseidUserDetails foundUser = new OseidUserDetails("Not_Registered");
		foundUser.setUserId(uid);
		List<OseidUserDetails> foundList = oseidUserDetailsRepository.findAll();
		if(foundList.size() > 0) {
			LOGGER.info("Found registered users");
			for(OseidUserDetails o : foundList) {
				if(Objects.equals(id, o.getUserId().getId())) {
					LOGGER.info("Found user with id: "+id);
					foundUser = o;
					return foundUser;
				}
			}		
		}else {
			LOGGER.info("No User Registered");			
		}
		LOGGER.info("User with id: "+id+", not registered");
		return foundUser;
	}
	
}
