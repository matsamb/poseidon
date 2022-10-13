package com.auth2.oseidclient.oseidrule.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;
import com.auth2.oseidclient.oseidrule.service.SaveOseidRuleService;

@SpringBootTest
@AutoConfigureMockMvc
public class PutOseidRuleRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@MockBean
	private SaveOseidRuleService saveOseidRuleService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(put("/rule"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenARegisteredRule_whenPutOseidRule_thenItShouldReturnStatusIsOk() throws Exception{
		
		OseidRule max = new OseidRule();
		max.setId(1);
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(max);
		
		mockMvc
			.perform(put("/rule") 
				.param("id", "1")	
				.with(user("flex")
					.password("prex")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\",\"name\":\"mateson\",\"description\":\"yayo\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPutRule_thenItShouldReturnStatusIsBadRequest() throws Exception{
		
		OseidRule max = new OseidRule();
		max.setId(1);
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(max);
		
		mockMvc
			.perform(put("/rule") 
				.param("id", "1")	
				.with(user("flex")
					.password("prex")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
		
	}
	
	@Test
	public void givenANotRegisteredOseidRule_whenPutOseidRule_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		OseidRule notRegistred = new OseidRule();
		notRegistred.setId(-1);
		
		when(findOseidRuleByIdService.findOseidRuleById(1)).thenReturn(notRegistred);
		
		mockMvc
			.perform(put("/rule") 
				.param("id", "1")	
				.with(user("flex")
					.password("prex")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"1\",\"name\":\"mateson\",\"description\":\"yayo\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			;
		
	}
	
	
	
}
