package com.auth2.oseidclient.user.restcontrollerIT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.user.service.DeleteUserByUsernameService;
import com.auth2.oseidclient.user.service.FindUserByUsernameService;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteUserByEmailRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindUserByUsernameService findUserByUsernameService;
	
	@MockBean
	private DeleteUserByUsernameService deleteUserByUsernameService;

	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(delete("/user"))
					.apply(springSecurity())
					.build()
					;
	}
	
	@Test
	public void givenMaxRegisteredUser_whenDeleteByEmailCalled_thenItShouldReturnStatusOk() throws Exception{
		
		OseidUserDetails max = new OseidUserDetails("sir@sir.com");
		max.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		max.setRoles("ROLE_USER");
		max.setEnabled(true);
		max.setLocked(false);
				
		when(findUserByUsernameService.findUserByUsername("sir@sir.com")).thenReturn(max);
		
		mockMvc
			.perform(delete("/user")
				.with(user("admin")
					.password("pass")
					.roles("ADMIN"))
				.param("username", "sir@sir.com"))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void givenMaxRegisteredUser_whenDeleteByEmailCalled_thenDeleteUserMethodIShouldBeCalledOnceAndStatusIsOk() throws Exception{
		
		OseidUserDetails max = new OseidUserDetails("sir@sir.com");
		max.setPassword("$2a$10$5p5eFzge8lX5kCRtwouZNu9zc/IShygTYvb6agG2CCkbBGoZIFYNK");
		max.setRoles("ROLE_USER");
		max.setEnabled(true);
		max.setLocked(false);
				
		when(findUserByUsernameService.findUserByUsername("sir@sir.com")).thenReturn(max);
		
		mockMvc
			.perform(delete("/user")
				.with(user("admin")
					.password("pass")
					.roles("ADMIN"))
				.param("username", "sir@sir.com"))
			.andExpect(status().isOk())
			;
		
		verify(deleteUserByUsernameService, times(1)).deleteUserByUsername("sir@sir.com");
		
	}
	
	@Test
	public void givenNotRegisteredUser_whenDeleteUserByEmailCalled_thenItShouldReturnStatusNotFound() throws Exception{
		
		OseidUserDetails defaultNotRegistered = new OseidUserDetails("Not_Registered");
		
		when(findUserByUsernameService.findUserByUsername("sir@sir.com")).thenReturn(defaultNotRegistered);		
		
		mockMvc
			.perform(delete("/user")
				.with(user("max")
					.password("pass")
					.roles("ADMIN"))
				.param("username", "sir@sir.com"))
			.andExpect(status().isNotFound())
			;
	}
	
}
