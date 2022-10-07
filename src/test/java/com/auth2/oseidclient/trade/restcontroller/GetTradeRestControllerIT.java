package com.auth2.oseidclient.trade.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.trade.service.FindTradeByAccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class GetTradeRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindTradeByAccountService findTradeByAccountServiceMock;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/trade"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	public void testRegisteredTrade() throws Exception {
		
		List<Trade> result = new ArrayList<>();
		
		Trade bid = new Trade("max");
		bid.setDealName("bId");
		bid.setTradeId(1);
		Trade bod = new Trade("max");
		bod.setDealName("bOd");
		
		result.add(bod);
		result.add(bid);
		bod.setTradeId(2);
		
		when(findTradeByAccountServiceMock.findTradeByAccount("max")).thenReturn(result);
	
		mockMvc.perform(get("/trade")
					.with(user("admin")
						.password("admin")
						.roles("ADMIN"))
					.param("account", "max"))
				.andExpect(status().isOk());
				
	
	}
	
	@Test
	public void testNotRegisteredBid() throws Exception {
		
		List<Trade> result = new ArrayList<>();
		
		Trade notRegistered = new Trade("Not_Registered");
		notRegistered.setTradeId(-1);
		result.add(notRegistered);
		
		when(findTradeByAccountServiceMock.findTradeByAccount("lex")).thenReturn(result);
	
		mockMvc.perform(get("/trade")
					.with(user("dax")
						.password("sax")
						.roles("ADMIN"))
					.param("account", "lex"))
				.andExpect(status().isOk());
				
	
	}
	
}
