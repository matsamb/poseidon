package com.auth2.oseidclient.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.service.BidService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BidService bidService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					//.defaultRequest(get("/bid"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Nested
	@DisplayName("GetBid")
	class GetBid{
		
/*		@BeforeEach
		public void setUp(WebApplicationContext webApplicationContext) {
			
			mockMvc = MockMvcBuilders
						.webAppContextSetup(webApplicationContext)
						.defaultRequest(get("/bid"))
						.apply(springSecurity())
						.build();
			
		}*/
		
		
		@Test
		public void testRegisteredBid() throws Exception {
			
			List<Bid> result = new ArrayList<>();
			
			Bid bid = new Bid("max");
			bid.setDealName("bId");
			Bid bod = new Bid("max");
			bod.setDealName("bOd");
			result.add(bod);
			result.add(bid);
			
			when(bidService.findBidByAccount("max")).thenReturn(result);
		
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
			
			when(bidService.findBidByAccount("lex")).thenReturn(result);
		
			mockMvc.perform(get("/bid")
						.with(user("dax")
							.password("sax")
							.roles("ADMIN"))
						.param("account", "lex"))
					.andExpect(status().isOk());
					
		
		}
	}
	
	@Nested
	@DisplayName("PostBid")
	class PostBid{
		
		@Test
		public void givenARequestBodyMissingMandatoryBidQuantityField_whenPostBidCalled_thenItShouldReturnBadRequest() throws Exception {
		
			Bid bid = new Bid();
			bid.setAccount("busta");
			bid.setType("cypher");
			bid.setBidQuantity(45d);
			
			List<Bid> bidList = new ArrayList<>();
			bidList.add(bid); 
			
			when(bidService.findBidByAccount("busta")).thenReturn(bidList);
			
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
			
			when(bidService.findBidByAccount("busta")).thenReturn(bidList);
			
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
			
			when(bidService.findBidByAccount("busta")).thenReturn(bidList);
			
			mockMvc
				.perform(post("/bid")
					.with(user("max")
						.password("ted")
						.roles("USER")))
				.andExpect(status().isBadRequest())
				;
			
		}
		
	}
	
	@Nested
	@DisplayName("PutBid")
	class PutBid{
		
		@Test
		public void givenARegisteredBid_whenPutBid_thenItShouldReturnStatusIsOk() throws Exception {
			
			Bid bid = new Bid(); 
			bid.setBidListId(1);
			bid.setType("cypher");
			bid.setAccount("busta");
			bid.setBidQuantity(45d);
			
			
			when(bidService.findBidById(1)).thenReturn(bid);
			
			mockMvc
				.perform(put("/bid")
					.with(user("dax")
						.password("sax")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"bidListId\":1,\"account\":\"busta\",\"type\":\"cypher\",\"bidQuantity\":45}")
					.accept(MediaType.APPLICATION_JSON)
					.param("bidListId", "1"))
				.andExpect(status().isOk())
				;
			
		}
		
		@Test
		public void givenANotRegisteredBid_whenPutBid_thenItShouldReturnStatusIsNotFound() throws Exception {
			
			Bid bid = new Bid();
			bid.setBidListId(-1);
			
			when(bidService.findBidById(1)).thenReturn(bid);
			
			mockMvc
			.perform(put("/bid")
					.with(user("dax")
						.password("sax")
						.roles("ADMIN"))
					.param("bidListId", "1")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"bidListId\":1,\"account\":\"busta\",\"type\":\"cypher\",\"bidQuantity\":45}")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				;
			
		}
		
		@Test
		public void givenAMissingRequestBody_whenPutBid_thenItShouldReturnStatusIsBadRequest() throws Exception {
			
			Bid bid = new Bid();
			bid.setBidListId(1);
			
			when(bidService.findBidById(1)).thenReturn(bid);
			
			mockMvc
				.perform(put("/bid")
					.with(user("dax")
						.password("sax")
						.roles("ADMIN"))
					.param("bidListId", "1"))
				.andExpect(status().isBadRequest())
				;
			
		}
		
	}
	
	@Nested
	@DisplayName("DeleteBid")
	class DeleteBid{
		
/*		@BeforeEach
		public void setUp(WebApplicationContext webApplicationContext) {
			
			mockMvc = MockMvcBuilders
						.webAppContextSetup(webApplicationContext)
						.defaultRequest(delete("/bid"))
						.apply(springSecurity())
						.build()
						;
			
		}*/

		@Test
		public void givenARegisteredBidId_whenDeleteBidCalled_thenItShouldReturnStatusOk() throws Exception{
			
			Bid bid = new Bid();
			bid.setBidListId(1);
			
			when(bidService.findBidById(1)).thenReturn(bid);
			
			mockMvc.perform(delete("/bid")
						.with(user("mate")
							.password("mass")
							.roles("ADMIN"))
						.param("bidlistid", "1"))
					.andExpect(status().isOk())
					;

		}
		
		@Test
		public void givenANotRegisteredBidId_whenDeleteBidCalled_thenItShouldReturnStatusNotFound() throws Exception{
			
			Bid bid = new Bid();
			bid.setBidListId(-1);
			
			when(bidService.findBidById(1)).thenReturn(bid);
			
			mockMvc.perform(delete("/bid")
						.with(user("mate")
							.password("mass")
							.roles("ADMIN"))
						.param("bidlistid", "1"))
					.andExpect(status().isNotFound())
					;

		}
		
	}
	
}
