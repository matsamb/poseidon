package com.auth2.oseidclient.restcontroller;

import java.net.URI;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.DTO.OseidUser;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.service.user.FindUserByEmailService;
import com.auth2.oseidclient.service.user.SaveOseidUserDetailsService;

@RestController
@RolesAllowed("ROLE_ADMIN")
public class PostUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("PostUserRestController");
	
	@Autowired
	private FindUserByEmailService findUserByEmailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaveOseidUserDetailsService saveOseidUserDetailsService;
	
	PostUserRestController(FindUserByEmailService findUserByEmailService
			,SaveOseidUserDetailsService saveOseidUserDetailsService
			){
		this.findUserByEmailService = findUserByEmailService;
		this.saveOseidUserDetailsService = saveOseidUserDetailsService;
	}
	
	@PostMapping("/user")
	public ResponseEntity<OseidUser> addUser(@RequestBody OseidUser oseidUser){
		
		OseidUserDetails newUser = new OseidUserDetails();
		
		if(Objects.isNull(oseidUser)) {
			
			LOGGER.info("Request Body is empty");
			return ResponseEntity.noContent().build();
			
		}else  {
		
			if (findUserByEmailService.findUserByEmail(oseidUser.getEmail()).getEmail() == "Not_Registered") {
				
				newUser.setEmail(oseidUser.getEmail());
				newUser.setPassword(passwordEncoder.encode(oseidUser.getPassword()));
				newUser.setRoles(oseidUser.getRole());
				newUser.setEnabled(true);
				newUser.setLocked(false);
				
				LOGGER.info(oseidUser.getEmail()+" loaded into database");
				saveOseidUserDetailsService.saveUserDetails(newUser);
				
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/user")
						.buildAndExpand("?email="+newUser.getEmail()).toUri();
				
				LOGGER.debug("Posted User "+newUser.getEmail()+" URI created");
				LOGGER.info("Posted User "+newUser.getEmail()+" created");
				
				return ResponseEntity.created(location).build();
			
			}else {
				LOGGER.info("User: "+oseidUser.getEmail()+", all ready registered");
				return ResponseEntity.ok(oseidUser);
			}		
		
		}
		
		
	}
	
}
