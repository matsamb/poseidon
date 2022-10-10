package com.auth2.oseidclient.bid.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.bid.service.DeleteBidService;
import com.auth2.oseidclient.bid.service.FindBidByIdService;
import com.auth2.oseidclient.entity.Bid;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteBidRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DeleteBidService deleteBidService;
	
	@MockBean
	private FindBidByIdService findBidByIdService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(delete("/bid"))
					.apply(springSecurity())
					.build()
					;
		
	}

	@Test
	public void givenARegisteredBidId_whenDeleteBidCalled_thenItShouldReturnStatusOk() throws Exception{
		
		Bid bid = new Bid();
		bid.setBidListId(1);
		
		when(findBidByIdService.findBidById(1)).thenReturn(bid);
		
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
		
		when(findBidByIdService.findBidById(1)).thenReturn(bid);
		
		mockMvc.perform(delete("/bid")
					.with(user("mate")
						.password("mass")
						.roles("ADMIN"))
					.param("bidlistid", "1"))
				.andExpect(status().isNotFound())
				;

	}
	
}
