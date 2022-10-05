package com.auth2.oseidclient.bid.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.entity.Bid;

@SpringBootTest
@AutoConfigureMockMvc
public class GetBidRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindBidByAccountService findBidByAccountServiceMock;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/bid"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	public void testRegisteredBid() throws Exception {
		
		List<Bid> result = new ArrayList<>();
		
		Bid bid = new Bid("max");
		bid.setDealName("bId");
		Bid bod = new Bid("max");
		bod.setDealName("bOd");
		result.add(bod);
		result.add(bid);
		
		when(findBidByAccountServiceMock.findBidByAccount("max")).thenReturn(result);
	
		mockMvc.perform(get("/bid")
					.with(user("admin")
						.password("admin")
						.roles("ADMIN"))
					.param("account", "max"))
				.andExpect(status().isOk());
				
	
	}
	
	@Test
	public void testNotRegisteredBid() throws Exception {
		
		List<Bid> result = new ArrayList<>();
		
		Bid notRegistered = new Bid("Not_Registered");
		result.add(notRegistered);
		
		when(findBidByAccountServiceMock.findBidByAccount("lex")).thenReturn(result);
	
		mockMvc.perform(get("/bid")
					.with(user("dax")
						.password("sax")
						.roles("ADMIN"))
					.param("account", "lex"))
				.andExpect(status().isOk());
				
	
	}
	
}
