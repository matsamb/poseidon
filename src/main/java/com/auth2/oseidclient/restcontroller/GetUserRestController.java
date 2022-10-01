package com.auth2.oseidclient.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.OseidUser;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.service.user.FindUserByEmailService;

@RestController
@RolesAllowed("ROLE_ADMIN")
public class GetUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("GetUserRestController");
		
	@Autowired
	private FindUserByEmailService findUserByEmailService;
	
	GetUserRestController(FindUserByEmailService findUserByEmailService){
		this.findUserByEmailService = findUserByEmailService;
	}
	
	@GetMapping("/user")//?email=<email>
	public OseidUserDetails findUser(@Param(value = "email") String email) {
		
	//	OseidUser userToDisplay = new OseidUser();
		OseidUserDetails foundUser = findUserByEmailService.findUserByEmail(email);
		
		if(foundUser.getEmail() != "N_A") {
			LOGGER.info("Found user with email :"+foundUser);
		}else {
			LOGGER.info("No registered user with email :"+foundUser);
		}
		
		return foundUser;		
	}
	
}
