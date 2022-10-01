package com.auth2.oseidclient.restcontrollerIT;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.service.user.FindUserByEmailService;
import com.auth2.oseidclient.utils.WithMockOseidclientUser;

@SpringBootTest
@AutoConfigureMockMvc
public class GetUserRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindUserByEmailService findUserByEmailServiceMock;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/user"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	public void givenMaxRegistered_whenGetWhithEmailMaxCalled_thenMaxDetailsShoulBeFound() throws Exception {
		
		OseidUserDetails user = new OseidUserDetails("max");
		user.setRoles("ROLE_ADMIN");
		user.setEnabled(true);
		user.setLocked(false);
		
		when(findUserByEmailServiceMock.findUserByEmail("max")).thenReturn(user);
		
		mockMvc
			.perform(get("/user").with(
					SecurityMockMvcRequestPostProcessors.user("max")
					.password("pass").roles("USER","ADMIN"))
				.param("email", "max"))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void givenNotRegisteredUser_whenGetWhithEmailMaxCalled_thenMaxDetailsShoulBeFound() throws Exception {
		
		OseidUserDetails user = new OseidUserDetails("N_A");
		user.setRoles("ROLE_ADMIN");
		user.setEnabled(true);
		user.setLocked(false);
		
		when(findUserByEmailServiceMock.findUserByEmail("max")).thenReturn(user);
		
		mockMvc
			.perform(get("/user").with(
					SecurityMockMvcRequestPostProcessors.user("max")
					.password("pass").roles("USER","ADMIN"))
				.param("email", "max"))
			.andExpect(status().isOk());
		
	}
	
}
