package com.auth2.oseidclient.trade.restcontroller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

import com.auth2.oseidclient.trade.service.SaveTradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostTradeRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SaveTradeService saveTradeService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.defaultRequest(post("/trade"))
				.apply(springSecurity())
				.build()
				;
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPostTradeCalled_thenItShouldReturnBadRestStatus() throws Exception{
		
		mockMvc
			.perform(post("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	public void givenANewTrade_whenPostTradeCalled_thenStatusIsCreatedShouldBeReturned() throws Exception{
		
		mockMvc
			.perform(post("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tradeId\":1,\"account\":\"busta\",\"type\":\"cypher\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			;
	}
	
	@Test
	public void givenANullRequiredFieldAccoountInRequestBody_whenPostTradeCalled_thenStatusBadRequestShouldBeReturned() throws Exception{
		
		mockMvc
			.perform(post("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tradeId\":1,\"account\": ,\"type\":\"cypher\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
	}
	
}
