package com.auth2.oseidclient.service;

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
public class UserService {

	public static final Logger LOGGER = LogManager.getLogger("UserService");

	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;

	UserService(OseidUserDetailsRepository oseidUserDetailsRepository){
		this.oseidUserDetailsRepository = oseidUserDetailsRepository;
	}
	
	public void saveUserDetails(OseidUserDetails newOseidUser) {
		LOGGER.info("loading new Oauth User: "+newOseidUser.getEmail()+", into database");
		oseidUserDetailsRepository.save(newOseidUser);	
	}
	
	public OseidUserDetails findUserByUsername(String username) {

		OseidUserDetails foundUser = new OseidUserDetails();
		foundUser.setLocked(false);
		LOGGER.info(oseidUserDetailsRepository.findByUsername(username).isPresent());
		if (oseidUserDetailsRepository.findByUsername(username).isPresent()) {
			foundUser = oseidUserDetailsRepository.findByUsername(username).get();
			LOGGER.info("User registered: "+foundUser.getUsername());
		}else {
			LOGGER.info("User not regidtred");
			foundUser.setEmail("Not_Registered");
			foundUser.setUsername("Not_Registered");
		}
		LOGGER.info(foundUser);
		return foundUser;
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
	
	public void deleteUserByUsername(String username) {
		LOGGER.info("User: "+username+", deleted");
		oseidUserDetailsRepository.deleteById(username);
		
	}
	
}
