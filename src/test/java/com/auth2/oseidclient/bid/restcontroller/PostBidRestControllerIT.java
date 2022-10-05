package com.auth2.oseidclient.bid.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.auth2.oseidclient.bid.entity.Bid;
import com.auth2.oseidclient.bid.service.AddBidService;
import com.auth2.oseidclient.bid.service.FindBidByAccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostBidRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AddBidService addBidService;
	
	@MockBean
	private FindBidByAccountService findBidByAccountService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(post("/bid"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenARequestBodyMissingMandatoryBidQuantityField_whenPostBidCalled_thenItShouldReturnBadRequest() throws Exception {
	
		Bid bid = new Bid();
		bid.setAccount("busta");
		bid.setType("cypher");
		bid.setBidQuantity(45d);
		
		List<Bid> bidList = new ArrayList<>();
		bidList.add(bid);
		
		when(findBidByAccountService.findBidByAccount("busta")).thenReturn(bidList);
		
		mockMvc
			.perform(post("/bid")
				.with(user("max")
					.password("ted")
					.roles("USER"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"account\":\"busta\",\"type\":\"cypher\",\"bidQuantity\":}")//45
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
		
	}
	
	@Test
	public void givenANewBid_whenPostBidCalled_thenItSchouldReturnIsCreatedStatus() throws Exception {
		
		Bid bid = new Bid();
		bid.setAccount("Not_Registered");
		List<Bid> bidList = new ArrayList<>();
		bidList.add(bid);
		
		when(findBidByAccountService.findBidByAccount("busta")).thenReturn(bidList);
		
		mockMvc
			.perform(post("/bid")
				.with(user("max")
					.password("ted")
					.roles("USER"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"account\":\"busta\",\"type\":\"cypher\",\"bidQuantity\":45}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			;
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPostBidCalled_thenItShouldReturnBadRequestStatus() throws Exception {
	
		Bid bid = new Bid();
		bid.setAccount("busta");
		bid.setType("cypher");
		bid.setBidQuantity(45d);
		
		List<Bid> bidList = new ArrayList<>();
		bidList.add(bid);
		
		when(findBidByAccountService.findBidByAccount("busta")).thenReturn(bidList);
		
		mockMvc
			.perform(post("/bid")
				.with(user("max")
					.password("ted")
					.roles("USER")))
			.andExpect(status().isBadRequest())
			;
		
	}

	
}
