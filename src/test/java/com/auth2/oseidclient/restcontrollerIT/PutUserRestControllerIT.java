package com.auth2.oseidclient.restcontrollerIT;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.auth2.oseidclient.user.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.FindUserByEmailService;
import com.auth2.oseidclient.user.service.SaveOseidUserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
public class PutUserRestControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindUserByEmailService mockFindUserByEmailService;
	
	@MockBean
	private SaveOseidUserDetailsService mockSaveOseidUserDetailsService;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(put("/user"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenRegisteredUser_whenPutPerformedWithOutChange_thenItShouldReturnStatusIsOk() throws Exception{
		
		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
		newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		newUser.setRoles("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setLocked(false);
				
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(newUser);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		
		
		mockMvc
			.perform(put("/user")
				.with(user("user")
					.password("pass")
					.roles("ADMIN"))
				.param("email", "sir@sir.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_USER\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			;
		
		
	}
	
	@Test
	public void givenRegisteredUser_whenPutPerformedWithChange_thenItShouldReturnStatusIsOk() throws Exception{
		
		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
		newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		newUser.setRoles("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setLocked(false);
				
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(newUser);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		
		
		mockMvc
			.perform(put("/user")
				.with(user("user")
					.password("pass")
					.roles("ADMIN"))
				.param("email", "sir@sir.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_ADMIN\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			;
		
		
	}
	
	@Test
	public void givenNotRegisteredUser_whenPutPerformed_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
		newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		newUser.setRoles("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setLocked(false);
		
		OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
		
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(defaultNotRegistered);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		
		
		mockMvc
			.perform(put("/user")
				.with(user("user")
					.password("pass")
					.roles("ADMIN"))
				.param("email", "sir@sir.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"sir@sir.com\",\"password\":\"sir\",\"role\":\"ROLE_ADMIN\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			;
		
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPutMapping_thenBadRequestStatusShouldBeReturned() throws Exception{
		
		OseidUserDetails newUser = new OseidUserDetails("sir@sir.com");
		newUser.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		newUser.setRoles("ROLE_USER");
		newUser.setEnabled(true);
		newUser.setLocked(false);
				
		when(mockFindUserByEmailService.findUserByEmail("sir@sir.com")).thenReturn(newUser);
		when(passwordEncoder.encode("sir")).thenReturn("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		
		
		mockMvc
			.perform(put("/user")
				.with(user("user")
					.password("pass")
					.roles("ADMIN"))
				.param("email", "sir@sir.com")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
		
		
	}
	
	
}
