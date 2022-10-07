package com.auth2.oseidclient.trade.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.trade.service.FindTradeByIdService;
import com.auth2.oseidclient.trade.service.SaveTradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class PutTradeRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindTradeByIdService findTradeByIdService;
	
	@MockBean
	private SaveTradeService saveTradeService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.defaultRequest(put("/trade"))
				.apply(springSecurity())
				.build()
				;
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPutTradeCalled_thenItShouldReturnBadRestStatus() throws Exception{
		
		Trade trade  = new Trade();
		trade.setTradeId(1);
		
		when(findTradeByIdService.findTradeByTradeId(1)).thenReturn(trade);
		
		mockMvc
			.perform(put("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.param("tradeid", "0")
				.contentType(MediaType.APPLICATION_JSON)
				.content("")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	public void givenAnExistingTrade_whenPutTradeCalled_thenStatusIsOkShouldBeReturned() throws Exception{
		
		Trade trade  = new Trade();
		trade.setTradeId(1);
		trade.setAccount("busta");
		trade.setType("toggle");
		
		when(findTradeByIdService.findTradeByTradeId(1)).thenReturn(trade);		
		
		mockMvc
			.perform(put("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.param("tradeid", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tradeId\":1,\"account\":\"busta\",\"type\":\"cypher\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			;
	}
	
	@Test
	public void givenANullRequiredFieldAccountInRequestBody_whenPostTradeCalled_thenStatusBadRequestShouldBeReturned() throws Exception{
		
		Trade trade  = new Trade();
		trade.setTradeId(1);
		trade.setAccount("busta");
		trade.setType("toggle");
		
		when(findTradeByIdService.findTradeByTradeId(1)).thenReturn(trade);		
		
		mockMvc
			.perform(put("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.param("tradeid", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tradeId\":1,\"account\":,\"type\":\"cypher\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	public void givenANotRegisteredTrade_whenPostTradeCalled_thenStatusBadRequestShouldBeReturned() throws Exception{
		
		Trade trade  = new Trade();
		trade.setTradeId(-1);
	//	trade.setAccount("busta");
	//	trade.setType("toggle");
		
		when(findTradeByIdService.findTradeByTradeId(1)).thenReturn(trade);		
		
		mockMvc
			.perform(put("/trade")
				.with(user("taste")
					.password("date")
					.roles("ADMIN"))
				.param("tradeid", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tradeId\":1,\"account\":\"cyfi\",\"type\":\"cypher\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			;
	}
	
}
