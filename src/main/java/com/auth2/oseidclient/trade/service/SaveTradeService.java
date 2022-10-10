package com.auth2.oseidclient.trade.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@Service
@Transactional
public class SaveTradeService {

	public static final Logger LOGGER = LogManager.getLogger("PostTradeRestController");

	@Autowired
	private TradeRepository tradeRepository;
	
	SaveTradeService(TradeRepository tradeRepository){
		this.tradeRepository = tradeRepository;
	}

	public void saveTrade(Trade tradeToAdd) {
		LOGGER.info("Loading new trade: "+tradeToAdd.getAccount()+", into database");
		tradeRepository.save(tradeToAdd);
	}
	
	
	
}
