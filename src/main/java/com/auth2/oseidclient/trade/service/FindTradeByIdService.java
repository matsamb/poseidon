package com.auth2.oseidclient.trade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@Service
@Transactional
public class FindTradeByIdService {

	private static final Logger LOGGER = LogManager.getLogger("FindTradeByIdService");
	
	@Autowired
	private TradeRepository tradeRepository;
	
	FindTradeByIdService(TradeRepository tradeRepository){
		this.tradeRepository = tradeRepository;
	}

	public Trade findTradeByTradeId(Integer tradeId) {
		Trade trade = new Trade();
		int count = 0;
		LOGGER.info("Found trade: "+!tradeRepository.findAll().isEmpty());
		if(!tradeRepository.findAll().isEmpty() ) 
		 {
			for(Trade t : tradeRepository.findAll()){
				if(Objects.equals(t.getTradeId(), tradeId)) {
					LOGGER.info("Found trade with Id: "+tradeId);
					trade = t;
					count++;
				}
			}
		}
		if(count == 0) {
			LOGGER.info("No trade found for Id: "+tradeId);
			LOGGER.info("Return default not registered trade with Id: "+trade.getTradeId());		
			trade.setTradeId(-1);
		}
		return trade;
	}
	
	
	
	
}
