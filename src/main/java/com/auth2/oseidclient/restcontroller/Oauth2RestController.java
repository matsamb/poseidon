package com.auth2.oseidclient.restcontroller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.service.FindUserByUsernameService;
import com.auth2.oseidclient.service.SaveOseidUserDetailsService;
import com.auth2.oseidclient.user.service.UserIdHelper;

@RestController
public class Oauth2RestController {

	public static final Logger LOGGER = LogManager.getLogger("Oauth2RestController");
	
	@Autowired
	private FindUserByUsernameService findUserByUsernameService;
	
	@Autowired
	private SaveOseidUserDetailsService saveOseidUserDetailsService;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserIdHelper userIdHelper;

	
	Oauth2RestController(FindUserByUsernameService findUserByUsernameService
			,SaveOseidUserDetailsService saveOseidUserDetailsService
			,PasswordEncoder passwordEncoder
			,UserIdHelper userIdHelper
			){
		this.findUserByUsernameService = findUserByUsernameService;
		this.saveOseidUserDetailsService = saveOseidUserDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.userIdHelper = userIdHelper;
	}
	
	@GetMapping("/")
	public RedirectView oauth(OAuth2AuthenticationToken authentication) {
		
		Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
		LOGGER.info("email :"+attributes.get("email").toString());
		LOGGER.info("username :"+attributes.get("login"));
		String email = (String) attributes.get("email");
		String username = (String) attributes.get("login");
		LOGGER.info("ATTRIBUTES: "+attributes.toString());
		if(findUserByUsernameService.findUserByUsername(email).getUsername() == "Not_Registered") {
			LOGGER.info("New Oauth2 User: "+findUserByUsernameService.findUserByUsername(email).getEmail());
			OseidUserDetails newOseidUser = new OseidUserDetails();
			
			newOseidUser.setUserId(userIdHelper.createUserId());
			LOGGER.info("New user helper id" + newOseidUser.getUserId().getId());
			newOseidUser.setEmail(email);
			newOseidUser.setUsername(email);
			String OauthUserPass = passwordEncoder.encode(username);
			newOseidUser.setPassword(OauthUserPass);
			newOseidUser.setEnabled(true);
			newOseidUser.setLocked(false);
			newOseidUser.setRoles("ROLE_USER");
			
		//	attributes.toString();
			
		/*	HashMap<String, Object> attributesHashMap = new HashMap<>(); 
			for(String s : attributes.keySet()) {
				attributesHashMap.put(s, attributes.get(s));
			}*/
			//data too long to be loaded into DataBaseS
			//newOseidUser.setAttributes(attributesHashMap);
			
			LOGGER.info("New Oauth2 user: "+newOseidUser.getEmail()+", loaded to database");
			saveOseidUserDetailsService.saveUserDetails(newOseidUser);
			//oseidUserDetailsRepository.save(newOseidUser);
			
			
		}else {
			LOGGER.info("Registered Oauth2 User");
		}
		
		return new RedirectView("/home");
	}
	
/*	@GetMapping("/oauth2")
	public OAuth2User getOauth(OAuth2AuthenticationToken auth) {
		OAuth2User user = new OseidUserDetails();
		user = auth.getPrincipal();
		LOGGER.info(user.getAuthorities());
		LOGGER.info("TOKEN: "+auth);
		
		return user;
	}*/
	
}
