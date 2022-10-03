package com.auth2.oseidclient.user.restcontrollerIT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.ietf.jgss.Oid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.when;

import com.auth2.oseidclient.user.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.FindUserByEmailService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostUserRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private FindUserByEmailService mockFindUserByEmailService;
	
	@MockBean
	private SaveOseidUserDetailsService mockSaveOseidUserDetailsService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(post("/user"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void performPostNewUserAndGetStatusCreated() throws Exception {
		
		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
		newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		newUser.setRoles("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setLocked(false);
		
		OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
		
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(defaultNotRegistered);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		
		mockMvc
			.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"email\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_USER\"}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
			
	}
	
	@Test
	public void performPostNewUserAndVerifySaveUserCalledOnce() throws Exception {
		
		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
		newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		newUser.setRoles("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setLocked(false);
		
		OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
		
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(defaultNotRegistered);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		
		mockMvc
			.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"email\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_USER\"}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	
		verify(mockSaveOseidUserDetailsService, times(1)).saveUserDetails(newUser);
		
	}
	
	@Test
	public void performPostWtihEmptyBodyAndGetStatusBadRequest() throws Exception {
		
		OseidUserDetails registeredUser = new OseidUserDetails("sir@sir.com");
		registeredUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		registeredUser.setRoles("ROLE_USER");
		registeredUser.setEnabled(true);
		registeredUser.setLocked(false);
				
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(registeredUser);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
				
		mockMvc
			.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
			
	}
	
	@Test
	public void whenPerformPostWithExistingUser_thenItShouldReturnStatusOk() throws Exception {
		
		OseidUserDetails registeredUser = new OseidUserDetails("sir@sir.com");
		registeredUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		registeredUser.setRoles("ROLE_USER");
		registeredUser.setEnabled(true);
		registeredUser.setLocked(false);
				
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(registeredUser);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
	
		mockMvc
			.perform(post("/user").with(user("user").password("user").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"email\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_USER\"}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			
	}
}
