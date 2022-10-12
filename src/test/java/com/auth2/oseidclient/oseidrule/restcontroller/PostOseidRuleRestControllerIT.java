package com.auth2.oseidclient.oseidrule.restcontroller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.oseidrule.service.SaveOseidRuleService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostOseidRuleRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SaveOseidRuleService saveOseidRuleService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(post("/rule"))
					.apply(springSecurity())
					.build()
					;
		
	}
	

	@Test
	public void givenANewRule_whenPostRuleIsCalled_thenStatusCreatedShouldBeReturned() throws Exception{
		
		
		mockMvc
			.perform(post("/rule")
				.with(user("mate")
					.password("taste")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"\",\"name\":\"mate\",\"description\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			;
		
	}
	
	@Test
	public void givenANewRuleMissingNameConstraint_whenPostRuleIsCalled_thenStatusCreatedShouldBeReturned() throws Exception{
		
		
		mockMvc
			.perform(post("/rule")
				.with(user("mate")
					.password("taste")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"\",\"name\":\"\",\"description\":\"yeyo\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPostRuleIsCalled_thenStatusBadRequestShouldBeReturned() throws Exception{
		
		
		mockMvc
			.perform(post("/rule")
				.with(user("mate")
					.password("taste")
					.roles("ADMIN")))
			.andExpect(status().isBadRequest())
			;
		
	}
}
