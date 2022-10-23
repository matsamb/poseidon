package com.auth2.oseidclient.restcontroller;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.auth2.oseidclient.DTO.OseidUser;
import com.auth2.oseidclient.DTO.User;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.service.FindUserByUsernameService;
import com.auth2.oseidclient.service.UserService;
import com.auth2.oseidclient.user.service.UserIdHelper;

@RestController
@RolesAllowed("ADMIN")
public class UserRestController {

	public static final Logger LOGGER = LogManager.getLogger("UserRestController");

	@Autowired
	private UserIdHelper userIdHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	UserRestController(UserService userService, UserIdHelper userIdHelper, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.userIdHelper = userIdHelper;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/userid")
	public ResponseEntity<User> getUserById(@RequestParam Integer id) {
		
		LOGGER.always();
		User returnedUser = new User();
		OseidUserDetails foundUser = userService.findUserByUserId(id);
		
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
	
	@GetMapping("/user")//?username=<username>
	public OseidUserDetails findUser(@RequestParam String username) {
		
	//	OseidUser userToDisplay = new OseidUser();
		OseidUserDetails foundUser = userService.findUserByUsername(username);
		
		if(foundUser.getUsername() != "N_A") {
			LOGGER.info("Found user with username :"+foundUser);
		}else {
			LOGGER.info("No registered user with username :"+foundUser);
		}
		
		return foundUser;		
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody Optional<@Valid OseidUser> oseidUserOptional) {

		if (oseidUserOptional.isEmpty()) {

			LOGGER.info("Bad request, Request Body is empty");
			return ResponseEntity.badRequest().build();

		} else {

			OseidUser oseidUser = oseidUserOptional.get();

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<OseidUser>> violations = validator.validate(oseidUser);
			LOGGER.info("VALIDATION" + validator.validate(oseidUser));
			LOGGER.info("VIOLATION " + violations.size());

			if (violations.size() > 0) {

				LOGGER.info("Bad request, constraint violations: " + violations);
				return ResponseEntity.badRequest().build();

			} else {

				OseidUserDetails newUser = new OseidUserDetails();
				User user = new User();

				if (userService.findUserByUsername(oseidUser.getUsername())
						.getUsername() == "Not_Registered") {

					newUser.setUserId(userIdHelper.createUserId());
					LOGGER.info("New user helper id" + newUser.getUserId().getId());
					newUser.setUsername(oseidUser.getUsername());
					newUser.setEmail(oseidUser.getUsername());
					newUser.setFullname(oseidUser.getFullname());
					newUser.setPassword(passwordEncoder.encode(oseidUser.getPassword()));
					newUser.setRoles(oseidUser.getRole());
					newUser.setEnabled(true);
					newUser.setLocked(false);

					LOGGER.info(oseidUser + " loaded into database");
					userService.saveUserDetails(newUser);

					URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/user")
							.buildAndExpand("?username=" + newUser.getUsername()).toUri();

					LOGGER.debug("Posted User " + newUser.getUsername() + " URI created");
					LOGGER.info("Posted User " + newUser.getUsername() + " created");

					user.setId(newUser.getUserId().getId());
					user.setUsername(newUser.getUsername());
					user.setFullname(newUser.getFullname());
					user.setPassword(newUser.getPassword());
					user.setRole(newUser.getRoles());

					return ResponseEntity.created(location).body(user);

				} else {
					LOGGER.info("User: " + oseidUser.getEmail() + ", all ready registered");

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
	
	@PutMapping("/user") // ?username=<username>
	public ResponseEntity<OseidUser> updateUser(@RequestBody Optional<OseidUser> oseidUserOptional) {

		if (oseidUserOptional.isEmpty()) {
			LOGGER.info("Request Body is empty");
			return ResponseEntity.badRequest().build();

		} else {

			OseidUser oseidUser = oseidUserOptional.get();
			OseidUserDetails registeredUser = userService.findUserByUsername(oseidUser.getUsername());
			LOGGER.info("User: " + registeredUser.getUsername() + ", found");

			if (registeredUser.getUsername() == "Not_Registered") {

				LOGGER.info("User not registered");
				return ResponseEntity.notFound().build();

			} else {

				String password = passwordEncoder.encode(oseidUser.getPassword());
				LOGGER.info(password);
				OseidUserDetails copyRegisteredUser = (OseidUserDetails) registeredUser.clone();
				registeredUser.setPassword(password);
				registeredUser.setRoles(oseidUser.getRole());
				registeredUser.setEnabled(true);
				registeredUser.setLocked(false);
				registeredUser.setAttributes(null);
				LOGGER.info("Request Body brought changes: " + !Objects.equals(copyRegisteredUser, registeredUser));
//				if (!Objects.equals(copyRegisteredUser, registeredUser)) {
					LOGGER.info("Parameters altered and saved");
					userService.saveUserDetails(registeredUser);
//				}
				LOGGER.info("User: " + oseidUser.getUsername() + ", updated");
				return ResponseEntity.ok(oseidUser);
			}
		}
	}
	
	@DeleteMapping("/user")//?username=<username>
	public ResponseEntity<OseidUser> deleteUser(@RequestParam String username){
		
		OseidUser off = new OseidUser();
		
		if(userService.findUserByUsername(username).getUsername() != "Not_Registered") {
			LOGGER.info("User: "+username+", deleted");
			userService.deleteUserByUsername(username);	
			off.setEmail("deleted");
			return ResponseEntity.ok(off);
			
		}else {
			LOGGER.info("User: "+username+", not registered");
			return ResponseEntity.notFound().build();
		}
		

		
	}

}
