package com.auth2.oseidclient.bid.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
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

import com.auth2.oseidclient.bid.service.AddBidService;
import com.auth2.oseidclient.bid.service.FindBidByIdService;
import com.auth2.oseidclient.entity.Bid;

@SpringBootTest
@AutoConfigureMockMvc
public class PutBidRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindBidByIdService findBidByIdService;
	
	@MockBean
	private AddBidService addBidService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(put("/bid"))
					.apply(springSecurity())
					.build()
					;
	}
	
	@Test
	public void test() throws Exception {
		
		Bid bid = new Bid(); 
		bid.setBidListId(1);
		bid.setType("cypher");
		bid.setAccount("busta");
		bid.setBidQuantity(45d);
		
		
		when(findBidByIdService.findBidById(1)).thenReturn(bid);
		
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
	public void testNotFound() throws Exception {
		
		Bid bid = new Bid();
		bid.setBidListId(-1);
		
		when(findBidByIdService.findBidById(1)).thenReturn(bid);
		
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
	public void testNoBody() throws Exception {
		
		Bid bid = new Bid();
		bid.setBidListId(1);
		
		when(findBidByIdService.findBidById(1)).thenReturn(bid);
		
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
