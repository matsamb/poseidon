package com.auth2.oseidclient.trade.restcontroller;

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

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.trade.service.DeleteTradeService;
import com.auth2.oseidclient.trade.service.FindTradeByIdService;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteTradeRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindTradeByIdService findTradeByIdService;
	
	@MockBean
	private DeleteTradeService deleteTradeService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(delete("/trade"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenARegisteredId1_whenDeleteTradeCalled_thenItShouldReturnStatusIsOk() throws Exception{
		
		Trade trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("tex");
		trade.setType("flex");
		
		when(findTradeByIdService.findTradeByTradeId(1)).thenReturn(trade);
		
		mockMvc
			.perform(delete("/trade")
					.param("tradeid", "1")
					.with(user("max")
						.password("flex")
						.roles("ADMIN"))
					)
			.andExpect(status().isOk())
			;
				
	}
	
	@Test
	public void givenANotRegisteredId_whenDeleteTradeCalled_thenItShouldReturnStatusNotFound() throws Exception{
		
		Trade trade = new Trade();
		trade.setTradeId(-1);
		
		when(findTradeByIdService.findTradeByTradeId(1)).thenReturn(trade);
		
		mockMvc
			.perform(delete("/trade")
					.with(user("max")
						.password("flex")
						.roles("ADMIN"))
					.param("tradeid", "1"))
			.andExpect(status().isNotFound())
			;
				
	}
	
}
