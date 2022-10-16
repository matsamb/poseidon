package com.auth2.oseidclient.user.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.OseidUser;
import com.auth2.oseidclient.user.service.DeleteUserByUsernameService;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class DeleteUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("DeleteUserRestController");
	
	@Autowired
	private DeleteUserByUsernameService deleteUserByUsernameService;
	
	@Autowired
	private FindUserByUsernameService findUserByUsernameService;
	
	DeleteUserRestController(DeleteUserByUsernameService deleteUserByUsernameService
			, FindUserByUsernameService findUserByUsernameService
			){
		this.deleteUserByUsernameService = deleteUserByUsernameService;
		this.findUserByUsernameService = findUserByUsernameService;
	}
	
	@DeleteMapping("/user")//?username=<username>
	public ResponseEntity<OseidUser> deleteUser(@RequestParam String username){
		
		OseidUser off = new OseidUser();
		
		if(findUserByUsernameService.findUserByUsername(username).getUsername() != "Not_Registered") {
			LOGGER.info("User: "+username+", deleted");
			deleteUserByUsernameService.deleteUserByUsername(username);	
			off.setEmail("deleted");
			return ResponseEntity.ok(off);
			
		}else {
			LOGGER.info("User: "+username+", not registered");
			return ResponseEntity.notFound().build();
		}
		

		
	}
	
	
}
