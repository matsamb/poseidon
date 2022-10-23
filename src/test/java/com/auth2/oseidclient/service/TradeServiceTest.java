package com.auth2.oseidclient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;
	
	@InjectMocks
	private TradeService tradeService;
	
	@Test
	public void givenATrade_whenSaveTradeServiceCalled_thenRepositorySaveTradeMethodShouldBeCalledOnce() {
		
		Trade trade = new Trade();
		trade.setAccount("lax");
		
		Trade tradeWithId = new Trade();
		tradeWithId.setTradeId(1);
		tradeWithId.setAccount("lax");
		
		when(tradeRepository.saveAndFlush(trade)).thenReturn(tradeWithId);
		
		Integer returnedId = tradeService.saveTrade(trade);
				
		assertThat(returnedId).isEqualTo(tradeWithId.getTradeId());
	
	}
	
	@Test
	public void givenATrade_whenDeleteTrade_thenTradeRepositoryDeleteMethodShpuldBeCalledOnce() {
		
		Trade trade = new Trade();
		
		tradeService.deleteTrade(trade);
		
		verify(tradeRepository, times(1)).delete(trade);
		
	}
	
	@Nested
	@DisplayName("findTradeId")
	class findTradeId{
		
		@Test
		public void givenRegisteredTrade1_whenFindTradeByIdServiceCalled_thenItShouldReturnTrade1() {
			
			Trade trade1 = new Trade();
			trade1.setTradeId(1);
			Trade trade2 = new Trade();
			trade2.setTradeId(2);
			List<Trade> tradeList = new ArrayList<>();
			tradeList.add(trade1);
			tradeList.add(trade2);
			
			when(tradeRepository.findAll()).thenReturn(tradeList);
			
			Trade foundTrade = tradeService.findTradeByTradeId(1);
			
			assertThat(foundTrade).isEqualTo(trade1);
			
		}
		
		@Test
		public void givenANotRegisteredTrade_whenFindTradeByIdServiceCalled_thenItShouldReturnDefaultNotregisteredTrade() {
			
			Trade trade1 = new Trade();
			trade1.setTradeId(1);
			Trade trade2 = new Trade();
			trade2.setTradeId(2);
			Trade notRegisteredTrade = new Trade();
			notRegisteredTrade.setTradeId(-1);
			List<Trade> tradeList = new ArrayList<>();
			tradeList.add(trade1);
			tradeList.add(trade2);
			
			when(tradeRepository.findAll()).thenReturn(tradeList);
			
			Trade foundTrade = tradeService.findTradeByTradeId(3);
			
			assertThat(foundTrade).isEqualTo(notRegisteredTrade);
			
		}
		
		@Test
		public void givenNoRegisteredTrade_whenFindTradeByIdServiceCalled_thenItShouldReturnDefaultNotregisteredTrade() {

			Trade notRegisteredTrade = new Trade();
			notRegisteredTrade.setTradeId(-1);
			List<Trade> tradeList = new ArrayList<>();
			
			when(tradeRepository.findAll()).thenReturn(tradeList);
			
			Trade foundTrade = tradeService.findTradeByTradeId(3);
			
			assertThat(foundTrade).isEqualTo(notRegisteredTrade);
			
		}
		
	}
	
	@Nested
	@DisplayName("findTradeAccount")
	class findTradeAccount{
		
		@Test
		public void givenATradeWithAccountTrax_whenFindTradeByAccountWithMaxCalled_thenItShouldNotContainTrax() {
			
			Trade trade1 = new Trade();
			trade1.setTradeId(1);
			trade1.setAccount("max");
			Trade trade2 = new Trade();
			trade2.setTradeId(2);
			trade2.setAccount("max");
			Trade trade3 = new Trade();
			trade3.setTradeId(3);
			trade3.setAccount("trax");
			
			List<Trade> tradeList = new ArrayList<Trade>();
			tradeList.add(trade1);
			tradeList.add(trade2);
			tradeList.add(trade3);
			
			when(tradeRepository.findAll()).thenReturn(tradeList);
			
			List<Trade> resultList = tradeService.findTradeByAccount("max");
			
			assertThat(resultList).doesNotContain(trade3);
			
		}
		
		@Test
		public void givenANotRegisteredTrade_whenFindTradeByAccountWithMaxCalled_thenItShouldReturnDefaultNotRegisteredTrade() {
			
			Trade expectedTrade = new Trade();
			expectedTrade.setTradeId(-1);
			List<Trade> expectedList = new ArrayList<Trade>();
			expectedList.add(expectedTrade);
			
			Trade trade1 = new Trade();
			trade1.setTradeId(1);
			trade1.setAccount("trax");

			List<Trade> tradeList = new ArrayList<Trade>();
			tradeList.add(trade1);

			when(tradeRepository.findAll()).thenReturn(tradeList);
			
			List<Trade> resultList = tradeService.findTradeByAccount("max");
			
			assertThat(resultList).isEqualTo(expectedList);
			
		}
		
		@Test
		public void givenNoRegisteredTrade_whenFindTradeByAccountWithMaxCalled_thenItShouldReturnDefaultNotRegisteredTrade() {
			
			Trade expectedTrade = new Trade();
			expectedTrade.setTradeId(-1);
			List<Trade> expectedList = new ArrayList<Trade>();
			expectedList.add(expectedTrade);

			List<Trade> tradeList = new ArrayList<Trade>();

			when(tradeRepository.findAll()).thenReturn(tradeList);
			
			List<Trade> resultList = tradeService.findTradeByAccount("max");
			
			assertThat(resultList).isEqualTo(expectedList);
			
		}	
	}
	
}
