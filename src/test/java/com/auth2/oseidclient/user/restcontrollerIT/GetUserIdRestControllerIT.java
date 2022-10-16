package com.auth2.oseidclient.user.restcontrollerIT;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.auth2.oseidclient.entity.UserId;
import com.auth2.oseidclient.user.service.FindUserByUserIdService;

@SpringBootTest
@AutoConfigureMockMvc
public class GetUserIdRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindUserByUserIdService findUserByUserIdService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/userid"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenARegisteredUser_whenGetUserId_thenItShouldReturnStatusOk() throws Exception{
		
		UserId uid = new UserId(1);
		OseidUserDetails foundUser = new OseidUserDetails("lax");
		
		foundUser.setUserId(uid);;
		foundUser.setPassword("hi&1Jkkl");
		foundUser.setFullname("laxtexmex");
		foundUser.setRoles("ADMIN");
		
		when(findUserByUserIdService.findUserByUserId(1)).thenReturn(foundUser);
		
		mockMvc
			.perform(get("/userid")
					.param("id", "1")
					.with(user("lax")
						.password("laxx")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void givenANotRegisteredUser_whenGetUserId_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		UserId uid = new UserId(-1);
		OseidUserDetails notRegistered = new OseidUserDetails("Not_Registered");		
		notRegistered.setUserId(uid);;
		
		when(findUserByUserIdService.findUserByUserId(1)).thenReturn(notRegistered);
		
		mockMvc
			.perform(get("/userid")
					.param("id", "1")
					.with(user("lax")
						.password("laxx")
						.roles("ADMIN")))
			.andExpect(status().isNotFound())
			;
		
	}
	
	
}
