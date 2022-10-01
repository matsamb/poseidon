package com.auth2.oseidclient.user.restcontroller;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

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

import com.auth2.oseidclient.user.DTO.OseidUser;
import com.auth2.oseidclient.user.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.FindUserByEmailService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

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
	public ResponseEntity<OseidUser> addUser(@RequestBody Optional<OseidUser> oseidUserOptional){
		
		
		if(oseidUserOptional.isEmpty()) {
			
			LOGGER.info("Bad request, Request Body is empty");
			return ResponseEntity.badRequest().build();
			
		}else  {
		
			OseidUser oseidUser = oseidUserOptional.get();
			
			OseidUserDetails newUser = new OseidUserDetails();
			
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
