package com.auth2.oseidclient.trade.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.repository.TradeRepository;

@Service
public class FindTradeByAccountService {

	private static final Logger LOGGER = LogManager.getLogger("FindTradeByAccountService");
	
	@Autowired
	private TradeRepository tradeRepository;
	
	FindTradeByAccountService(TradeRepository tradeRepository){
		this.tradeRepository = tradeRepository;
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
	
	
	
}
