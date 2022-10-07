package com.auth2.oseidclient.trade.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.TradeDTO;
import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.trade.service.FindTradeByAccountService;

@RestController
public class GetTradeRestController {

	private static final Logger LOGGER = LogManager.getLogger("GetTradeRestController");
	
	@Autowired
	private FindTradeByAccountService findTradeByAccountService;
	
	GetTradeRestController(FindTradeByAccountService findTradeByAccountService){
		this.findTradeByAccountService = findTradeByAccountService;
	}
	
	@GetMapping("/trade")//?account=<account>
	public Iterable<TradeDTO> getTrade(@RequestParam String account){
		List<TradeDTO> tradeDtoList = new ArrayList<TradeDTO>();
		
		if(findTradeByAccountService.findTradeByAccount(account).get(0).getTradeId() == -1) {
			LOGGER.info("No trade found for account: "+account);
			
		}else {
			LOGGER.info("Trade found for account: "+account);
			TradeDTO tradeDto = new TradeDTO();
			tradeDto.setAccount(account);
			for(Trade t : findTradeByAccountService.findTradeByAccount(account)) {
				tradeDto.setTradeId(t.getTradeId());
				tradeDto.setType(t.getType());
				LOGGER.info("Trade added to display list: "+tradeDto);
				tradeDtoList.add((TradeDTO)tradeDto.clone());
				
			}
			
		}
		return tradeDtoList;
		
	}
	
}
