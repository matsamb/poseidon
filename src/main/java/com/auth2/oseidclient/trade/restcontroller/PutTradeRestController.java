package com.auth2.oseidclient.trade.restcontroller;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.TradeDTO;
import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.trade.service.FindTradeByIdService;
import com.auth2.oseidclient.trade.service.SaveTradeService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PutTradeRestController {

	public static final Logger LOGGER = LogManager.getLogger("PutTradeRestController");
	
	@Autowired
	private SaveTradeService saveTradeService;
	
	@Autowired
	private FindTradeByIdService findTradeByIdService;
	
	PutTradeRestController(SaveTradeService saveTradeService
			, FindTradeByIdService findTradeByIdService
			){
		this.saveTradeService = saveTradeService;
		this.findTradeByIdService = findTradeByIdService;
	}
	
	@PutMapping("/trade")//?tradeid=<tradeid>
	public ResponseEntity<TradeDTO> updateTrade(@RequestBody Optional<TradeDTO> tradeDtoOptional, @RequestParam Integer tradeid){
		
		if(tradeDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, RequestBody is empty");
			return ResponseEntity.badRequest().build();			
		}else {
			LOGGER.info("Request, is being processed");
			TradeDTO tradeDto = tradeDtoOptional.get();
			
			if(findTradeByIdService.findTradeByTradeId(tradeid).getTradeId() == -1){
				LOGGER.info("No registered trade with Id: "+tradeDto.getTradeId());
				return ResponseEntity.notFound().build();			
				
			}else {
				LOGGER.info("UpdatingTrade with Id: "+tradeDto.getTradeId());
			Trade tradeToUpdate = new Trade();
			
			tradeToUpdate.setAccount(tradeDto.getAccount());
			tradeToUpdate.setType(tradeDto.getType());
			
			LOGGER.info("loading into database");
			saveTradeService.saveTrade(tradeToUpdate);
			
			return  ResponseEntity.ok(tradeDto);
			
			}
			
		}
		
	}
	
}
