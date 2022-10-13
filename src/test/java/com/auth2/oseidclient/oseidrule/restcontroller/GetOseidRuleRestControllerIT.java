package com.auth2.oseidclient.oseidrule.restcontroller;

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
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;

@SpringBootTest
@AutoConfigureMockMvc
public class GetOseidRuleRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/rule"))
					.apply(springSecurity())
					.build()
					;
				
	}
	
	@Test
	public void givenARegisteredRule_whenGetOseidRule_thenItShouldReturnStatusIsOk() throws Exception{
		
		OseidRule tex = new OseidRule();
		tex.setId(1);
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(tex);
		
		mockMvc.perform(get("/rule")
					.param("id", "1")	
					.with(user("laex")
						.password("fleax")
						.roles("ADMIN")))
				.andExpect(status().isOk())
				;
		
	}
	
	@Test
	public void givenANotRegisteredRule_whenGetOseidRule_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		OseidRule notRegistered = new OseidRule();
		notRegistered.setId(-1);
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(notRegistered);
		
		mockMvc.perform(get("/rule")
					.param("id", "1")	
					.with(user("laex")
						.password("fleax")
						.roles("ADMIN")))
				.andExpect(status().isNotFound())
				;
		
	}
	
}
