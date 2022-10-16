package com.auth2.oseidclient.user.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.OseidUser;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;

@RestController
@RolesAllowed("ROLE_ADMIN")
public class GetUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("GetUserRestController");
		
	@Autowired
	private FindUserByUsernameService findUserByUsernameService;
	
	GetUserRestController(FindUserByUsernameService findUserByUsernameService){
		this.findUserByUsernameService = findUserByUsernameService;
	}
	
	@GetMapping("/user")//?email=<email>
	public OseidUserDetails findUser(@RequestParam String username) {
		
	//	OseidUser userToDisplay = new OseidUser();
		OseidUserDetails foundUser = findUserByUsernameService.findUserByUsername(username);
		
		if(foundUser.getUsername() != "N_A") {
			LOGGER.info("Found user with username :"+foundUser);
		}else {
			LOGGER.info("No registered user with username :"+foundUser);
		}
		
		return foundUser;		
	}
	
}
