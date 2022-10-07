package com.auth2.oseidclient.trade.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class FindTradeByAccountServiceTest {

	@Mock
	private TradeRepository tradeRepository;
	
	@InjectMocks
	private FindTradeByAccountService findTradeByAccountService;
	
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
		
		List<Trade> resultList = findTradeByAccountService.findTradeByAccount("max");
		
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
		
		List<Trade> resultList = findTradeByAccountService.findTradeByAccount("max");
		
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
		
		List<Trade> resultList = findTradeByAccountService.findTradeByAccount("max");
		
		assertThat(resultList).isEqualTo(expectedList);
		
	}
	
}
