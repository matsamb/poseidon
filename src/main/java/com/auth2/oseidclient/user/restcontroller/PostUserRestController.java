package com.auth2.oseidclient.user.restcontroller;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;

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
import com.auth2.oseidclient.DTO.User;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;
import com.auth2.oseidclient.user.service.UserIdHelper;

import javax.validation.Validator;

@RestController
@RolesAllowed("ADMIN")
public class PostUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("PostUserRestController");
	
	@Autowired
	private UserIdHelper userIdHelper;
	
	@Autowired
	private FindUserByUsernameService findUserByUsernameService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaveOseidUserDetailsService saveOseidUserDetailsService;
	
	PostUserRestController(UserIdHelper userIdHelper
			, FindUserByUsernameService findUserByUsernameService
			,SaveOseidUserDetailsService saveOseidUserDetailsService
			){
		this.userIdHelper = userIdHelper;
		this.findUserByUsernameService = findUserByUsernameService;
		this.saveOseidUserDetailsService = saveOseidUserDetailsService;
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody Optional<@Valid OseidUser> oseidUserOptional){
		
		
		if(oseidUserOptional.isEmpty()) {
			
			LOGGER.info("Bad request, Request Body is empty");
			return ResponseEntity.badRequest().build();
			
		}else  {
		
			OseidUser oseidUser = oseidUserOptional.get();
			
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();	
			Set<ConstraintViolation<OseidUser>> violations = validator.validate(oseidUser);
			LOGGER.info("VALIDATION"+validator.validate(oseidUser));
			LOGGER.info("VIOLATION "+violations.size());
			
			if(violations.size()>0) {
				
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();

			}else {
			
			OseidUserDetails newUser = new OseidUserDetails();
			User user = new User();
			
			if (findUserByUsernameService.findUserByUsername(oseidUser.getUsername()).getUsername() == "Not_Registered") {
				
				newUser.setUserId(userIdHelper.createUserId());
				LOGGER.info("New user helper id"+newUser.getUserId().getId());
				newUser.setUsername(oseidUser.getUsername());
				newUser.setEmail(oseidUser.getUsername());
				newUser.setFullname(oseidUser.getFullname());
				newUser.setPassword(passwordEncoder.encode(oseidUser.getPassword()));
				newUser.setRoles(oseidUser.getRole());
				newUser.setEnabled(true);
				newUser.setLocked(false);
				
				LOGGER.info(oseidUser+" loaded into database");
				saveOseidUserDetailsService.saveUserDetails(newUser);
				
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/user")
						.buildAndExpand("?username="+newUser.getUsername()).toUri();
				
				LOGGER.debug("Posted User "+newUser.getUsername()+" URI created");
				LOGGER.info("Posted User "+newUser.getUsername()+" created");
				
				user.setId(newUser.getUserId().getId());
				user.setUsername(newUser.getUsername());
				user.setFullname(newUser.getFullname());
				user.setPassword(newUser.getPassword());
				user.setRole(newUser.getRoles());
				
				return ResponseEntity.created(location).body(user);
			
			}else {
				LOGGER.info("User: "+oseidUser.getEmail()+", all ready registered");
				
				user.setId(newUser.getUserId().getId());
				user.setUsername(newUser.getUsername());
				user.setFullname(newUser.getFullname());
				user.setPassword(newUser.getPassword());
				user.setRole(newUser.getRoles());
				return ResponseEntity.ok(user);
			}		
		}
		}
		
		
	}
	
}
