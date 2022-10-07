package com.auth2.oseidclient.trade.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteTradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;
	
	@InjectMocks
	private DeleteTradeService deleteTradeService;
	
	@Test
	public void test() {
		
		Trade trade = new Trade();
		
		deleteTradeService.deleteTrade(trade);
		
		verify(tradeRepository, times(1)).delete(trade);
		
	}
	
}
