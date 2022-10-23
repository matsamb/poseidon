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

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.service.OseidRatingService;
import com.auth2.oseidclient.service.TradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TradeService tradeService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Nested
	@DisplayName("postTrade")
	class postTrade {
		
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
	
	@Nested
	@DisplayName("putTrade")
	class putTrade {
		
		@Test
		public void givenAnEmptyRequestBody_whenPutTradeCalled_thenItShouldReturnBadRestStatus() throws Exception{
			
			Trade trade  = new Trade();
			trade.setTradeId(1);
			
			when(tradeService.findTradeByTradeId(1)).thenReturn(trade);
			
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
			
			when(tradeService.findTradeByTradeId(1)).thenReturn(trade);		
			
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
			
			when(tradeService.findTradeByTradeId(1)).thenReturn(trade);		
			
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
			
			when(tradeService.findTradeByTradeId(1)).thenReturn(trade);		
			
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
	
	@Nested
	@DisplayName("getTrade")
	class getTrade {
		
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
			
			when(tradeService.findTradeByAccount("max")).thenReturn(result);
		
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
			
			when(tradeService.findTradeByAccount("lex")).thenReturn(result);
		
			mockMvc.perform(get("/trade")
						.with(user("dax")
							.password("sax")
							.roles("ADMIN"))
						.param("account", "lex"))
					.andExpect(status().isOk());
					
		
		}
		
	}
	
	@Nested
	@DisplayName("deleteTrade")
	class deleteTrade {
		
		@Test
		public void givenARegisteredId1_whenDeleteTradeCalled_thenItShouldReturnStatusIsOk() throws Exception{
			
			Trade trade = new Trade();
			trade.setTradeId(1);
			trade.setAccount("tex");
			trade.setType("flex");
			
			when(tradeService.findTradeByTradeId(1)).thenReturn(trade);
			
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
			
			when(tradeService.findTradeByTradeId(1)).thenReturn(trade);
			
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
	
}
