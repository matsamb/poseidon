package com.auth2.oseidclient.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@Service
public class TradeService {

	public static final Logger LOGGER = LogManager.getLogger("TradeService");
	
	@Autowired
	private TradeRepository tradeRepository;
	
	TradeService(TradeRepository tradeRepository){
		this.tradeRepository = tradeRepository;
	}
	
	public Integer saveTrade(Trade tradeToAdd) {
		LOGGER.info("Loading new trade: "+tradeToAdd.getAccount()+", into database");
		Trade returnedTrade = tradeRepository.saveAndFlush(tradeToAdd);
		LOGGER.info(returnedTrade);
		return returnedTrade.getTradeId();
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
	
	public List<Trade> findTradeByAccount(String account) {
		LOGGER.info(tradeRepository.findAll());
		List<Trade> tradeList = new ArrayList<>();
		if(tradeRepository.findAll().isEmpty()) {
			LOGGER.info("No trade registered");
		}else {
			for(Trade t : tradeRepository.findAll()) {
				if(t.getAccount().contentEquals(account)) {
					tradeList.add(t);
					LOGGER.info(t+" added to result list");
				}
			}
		}
		if(tradeList.size()==0) {
			LOGGER.info("No trade found for account: "+account);
			Trade trade = new Trade();
			trade.setTradeId(-1);
			tradeList.add(trade);
		}else {
			LOGGER.info("Trade found for account: "+account);
		}
		
		return tradeList;
	}
	
	public void deleteTrade(Trade trade) {
		LOGGER.info("Deleting trade: "+trade.getTradeId());
		tradeRepository.delete(trade);
	}
	
}
