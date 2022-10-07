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
public class SaveTradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;
	
	@InjectMocks
	private SaveTradeService saveTradeService;
	
	@Test
	public void givenATrade_whenSaveTradeServiceCalled_thenRepositorySaveTradeMethodShouldBeCalledOnce() {
		
		Trade trade = new Trade();
		trade.setTradeId(1);
		
		saveTradeService.saveTrade(trade);
				
		verify(tradeRepository, times(1)).save(trade);
	
	}
	
}
