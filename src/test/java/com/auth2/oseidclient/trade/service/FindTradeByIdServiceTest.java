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
public class FindTradeByIdServiceTest {

	@Mock
	private TradeRepository tradeRepository;
	
	@InjectMocks
	private FindTradeByIdService findTradeByIdService;
	
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
		
		Trade foundTrade = findTradeByIdService.findTradeByTradeId(1);
		
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
		
		Trade foundTrade = findTradeByIdService.findTradeByTradeId(3);
		
		assertThat(foundTrade).isEqualTo(notRegisteredTrade);
		
	}
	
	@Test
	public void givenNoRegisteredTrade_whenFindTradeByIdServiceCalled_thenItShouldReturnDefaultNotregisteredTrade() {

		Trade notRegisteredTrade = new Trade();
		notRegisteredTrade.setTradeId(-1);
		List<Trade> tradeList = new ArrayList<>();
		
		when(tradeRepository.findAll()).thenReturn(tradeList);
		
		Trade foundTrade = findTradeByIdService.findTradeByTradeId(3);
		
		assertThat(foundTrade).isEqualTo(notRegisteredTrade);
		
	}
	
}
