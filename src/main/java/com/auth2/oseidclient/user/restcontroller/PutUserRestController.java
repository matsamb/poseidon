package com.auth2.oseidclient.user.restcontroller;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.OseidUser;
import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

@RestController
@RolesAllowed({ "ADMIN", "USER" })
public class PutUserRestController {

	public static final Logger LOGGER = LogManager.getLogger("PutUserRestController");

	@Autowired
	private FindUserByUsernameService findUserByUsernameService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaveOseidUserDetailsService saveOseidUserDetailsService;

	PutUserRestController(FindUserByUsernameService findUserByUsernameService) {
		this.findUserByUsernameService = findUserByUsernameService;
	}

	@PutMapping("/user") // ?username=<username>
	public ResponseEntity<OseidUser> updateUser(@RequestBody Optional<OseidUser> oseidUserOptional) {

		if (oseidUserOptional.isEmpty()) {
			LOGGER.info("Request Body is empty");
			return ResponseEntity.badRequest().build();

		} else {

			OseidUser oseidUser = oseidUserOptional.get();
			OseidUserDetails registeredUser = findUserByUsernameService.findUserByUsername(oseidUser.getUsername());
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
				if (!Objects.equals(copyRegisteredUser, registeredUser)) {
					LOGGER.info("Parameters altered and saved");
					saveOseidUserDetailsService.saveUserDetails(registeredUser);
				}
				LOGGER.info("User: " + oseidUser.getUsername() + ", updated");
				return ResponseEntity.ok(oseidUser);
			}
		}
	}

}
