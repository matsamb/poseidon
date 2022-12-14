package com.auth2.oseidclient.user.restcontroller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

@RestController
public class Oauth2RestController {

	public static final Logger LOGGER = LogManager.getLogger("Oauth2RestController");
	
	@Autowired
	private FindUserByUsernameService findUserByUsernameService;
	
	@Autowired
	private SaveOseidUserDetailsService saveOseidUserDetailsService;
	
	@Autowired
	private OseidUserDetailsRepository oseidUserDetailsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Oauth2RestController(FindUserByUsernameService findUserByUsernameService
			,SaveOseidUserDetailsService saveOseidUserDetailsService
			,PasswordEncoder passwordEncoder
			){
		this.findUserByUsernameService = findUserByUsernameService;
		this.saveOseidUserDetailsService = saveOseidUserDetailsService;
		this.passwordEncoder = passwordEncoder;
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
