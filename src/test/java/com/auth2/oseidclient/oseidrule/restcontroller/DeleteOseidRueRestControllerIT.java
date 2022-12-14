package com.auth2.oseidclient.oseidrule.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.oseidrule.service.DeleteOseidRueService;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteOseidRueRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@MockBean
	private DeleteOseidRueService deleteOseidRuleService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		 
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(delete("/rue"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void test() throws Exception {
		
		OseidLeru notRegistered = new OseidLeru();
		notRegistered.setId(-1);
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(notRegistered);
		
		mockMvc.perform(delete("/rue")
					.param("id", "1")
					.with(user("nwl")
						.password("nyjfk")
						.roles("Admin")))
					.andExpect(status().isOk())
		;
		
	}
	
/*	@Test
	public void registered() throws Exception {
		
		OseidLeru registered = new OseidLeru();
		registered.setId(1);
		registered.setName("late");
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(registered);
		
		mockMvc.perform(delete("/rue")
					.param("id", "1")
					.with(user("nwl")
						.password("nyjfk")
						.roles("Admin")))
					.andExpect(status().isNotFound())
		;
		
	}*/
}
