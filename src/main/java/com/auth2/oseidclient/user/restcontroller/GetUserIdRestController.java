package com.auth2.oseidclient.user.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.User;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.FindUserByUserIdService;

@RestController
public class GetUserIdRestController {

	public static final Logger LOGGER = LogManager.getLogger("GetUserIdRestController");
	
	@Autowired
	private FindUserByUserIdService findUserByUserIdService;
	
	GetUserIdRestController(FindUserByUserIdService findUserByUserIdService
			){
		this.findUserByUserIdService = findUserByUserIdService;
	}
	
	@GetMapping("/userid")
	public ResponseEntity<User> getUserById(@RequestParam Integer id) {
		
		LOGGER.always();
		User returnedUser = new User();
		OseidUserDetails foundUser = findUserByUserIdService.findUserByUserId(id);
		
		if(foundUser.getUsername() != "Not_Registered") {
			LOGGER.info("Found user with email :"+foundUser);
			returnedUser.setId(foundUser.getUserId().getId());
			returnedUser.setPassword(foundUser.getPassword());
			returnedUser.setUsername(foundUser.getUsername());
			returnedUser.setFullname(foundUser.getFullname());
			returnedUser.setRole(foundUser.getRoles());
			return ResponseEntity.ok(returnedUser);
		}else {
			LOGGER.info("No registered user with email :"+foundUser);
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
}
