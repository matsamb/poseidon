package com.auth2.oseidclient.trade.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.OseidRatingRepository;
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
		trade.setAccount("lax");
		
		Trade tradeWithId = new Trade();
		tradeWithId.setTradeId(1);
		tradeWithId.setAccount("lax");
		
		when(tradeRepository.saveAndFlush(trade)).thenReturn(tradeWithId);
		
		Integer returnedId = saveTradeService.saveTrade(trade);
				
		assertThat(returnedId).isEqualTo(tradeWithId.getTradeId());
	
	}
	
}
