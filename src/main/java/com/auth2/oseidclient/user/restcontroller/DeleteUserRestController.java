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
import com.auth2.oseidclient.user.service.DeleteUserByEmailService;
import com.auth2.oseidclient.user.service.FindUserByEmailService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class DeleteUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("DeleteUserRestController");
	
	@Autowired
	private DeleteUserByEmailService deleteUserByEmailService;
	
	@Autowired
	private FindUserByEmailService findUserByEmailService;
	
	DeleteUserRestController(DeleteUserByEmailService deleteUserByEmailService
			,FindUserByEmailService findUserByEmailService
			){
		this.deleteUserByEmailService = deleteUserByEmailService;
		this.findUserByEmailService = findUserByEmailService;
	}
	
	@DeleteMapping("/user")//?email=<email>
	public ResponseEntity<OseidUser> deleteUser(@RequestParam String email){
		
		OseidUser off = new OseidUser();
		
		if(findUserByEmailService.findUserByEmail(email).getEmail() != "Not_Registered") {
			LOGGER.info("User: "+email+", deleted");
			deleteUserByEmailService.deleteUserByEmail(email);	
			off.setEmail("deleted");
			return ResponseEntity.ok(off);
			
		}else {
			LOGGER.info("User: "+email+", not registered");
			return ResponseEntity.notFound().build();
		}
		

		
	}
	
}
