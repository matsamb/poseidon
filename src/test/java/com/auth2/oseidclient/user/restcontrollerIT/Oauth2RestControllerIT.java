package com.auth2.oseidclient.user.restcontrollerIT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.user.entity.OseidUserDetails;
import com.auth2.oseidclient.user.repository.OseidUserDetailsRepository;
import com.auth2.oseidclient.user.service.FindUserByEmailService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
public class Oauth2RestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindUserByEmailService mockFindUserByEmailService;
	
	@MockBean
	private SaveOseidUserDetailsService mockSaveOseidUserDetailsService;
	
//	@MockBean
//	private OseidUserDetailsRepository mockOseidUserDetailsRepository;
	
	@MockBean
	private PasswordEncoder mockPasswordEncoder;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/"))
					.apply(springSecurity())
					.build();
				
	}
	
	@Test
	public void givenAregisteredOauth2User_whenLoginPerformed_thenHeShouldBeRedirectedToHomePage() throws Exception {
		
		OAuth2User lax = new OAuth2User() {

			@Override
			public String getName() {
				return (String) getAttributes().get("name");
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			}
			
			@Override
			public Map<String, Object> getAttributes() {
				HashMap<String,Object> attributes = new HashMap<>();
				attributes.put("email", "max");
				attributes.put("name", "N_A");
		//		attributes.put("username", "N_A");
				attributes.put("sub", "N_A");
				return attributes;
			}
		};
		
		OseidUserDetails user = new OseidUserDetails("max");
		user.setRoles("ROLE_ADMIN");
		user.setEnabled(true);
		user.setLocked(false);
		
		when(mockFindUserByEmailService.findUserByEmail("max")).thenReturn(user);
		
		
		mockMvc
			.perform(get("/").with(oauth2Login().oauth2User(lax)))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/home"))
			;
				
	}
	
	@Test
	public void givenNotRegisteredOauth2User_whenLoginPerformed_thenHeShouldBeSavedOnce() throws Exception {
		
		OAuth2User lax = new OAuth2User() {

			@Override
			public String getName() {
				return (String) getAttributes().get("name");
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			}
			
			@Override
			public Map<String, Object> getAttributes() {
				HashMap<String,Object> attributes = new HashMap<>();
				attributes.put("email", "max");
				attributes.put("name", "N_A");
				attributes.put("sub", "N_A");
				return attributes;
			}
		};
		
		OseidUserDetails user = new OseidUserDetails("Not_Registered");
		user.setPassword(null);
		user.setRoles("ROLE_ADMIN");
		user.setEnabled(true);
		user.setLocked(false);
		
		OseidUserDetails laxOseid = new OseidUserDetails("max");
		laxOseid.setEnabled(true);
		laxOseid.setLocked(false);
		laxOseid.setRoles("ROLE_USER");
		
		when(mockFindUserByEmailService.findUserByEmail("max")).thenReturn(user);
		
		
		mockMvc
			.perform(get("/").with(oauth2Login().oauth2User(lax)))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/home"))
			;
		
		verify(mockSaveOseidUserDetailsService,times(1)).saveUserDetails(laxOseid);
		
	}
	
}
