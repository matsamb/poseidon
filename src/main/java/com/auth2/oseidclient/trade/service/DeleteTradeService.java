package com.auth2.oseidclient.trade.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@Service
public class DeleteTradeService {

	private static final Logger LOGGER = LogManager.getLogger("DeleteTradeRestController");

	@Autowired
	private TradeRepository tradeRepository;
	
	DeleteTradeService(TradeRepository tradeRepository){
		this.tradeRepository = tradeRepository;
	}

	public void deleteTrade(Trade trade) {
		LOGGER.info("Deleting trade: "+trade.getTradeId());
		tradeRepository.delete(trade);
	}
	
	
}
