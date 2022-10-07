package com.auth2.oseidclient.trade.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.TradeDTO;
import com.auth2.oseidclient.trade.service.DeleteTradeService;
import com.auth2.oseidclient.trade.service.FindTradeByIdService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class DeleteTradeRestController {

	private static final Logger LOGGER = LogManager.getLogger("DeleteTradeRestController");
	
	@Autowired
	private DeleteTradeService deleteTradeService;
	
	@Autowired
	private FindTradeByIdService findTradeByIdService;
	
	DeleteTradeRestController(DeleteTradeService deleteTradeService
			,FindTradeByIdService findTradeByIdService
			){
		this.deleteTradeService = deleteTradeService;
		this.findTradeByIdService = findTradeByIdService;
	}
	
	@DeleteMapping("/trade")//?tradeid=<tradeid>
	public ResponseEntity<TradeDTO> deleteTrade(@RequestParam Integer tradeid){
		
		LOGGER.info("Looking for trade with Id: "+tradeid);
		if(findTradeByIdService.findTradeByTradeId(tradeid).getTradeId() == -1) {
			LOGGER.info("Id: "+tradeid+", not registered");
			return ResponseEntity.notFound().build();
			
		}else {
			LOGGER.info("Deleting trade with Id: "+tradeid);
			deleteTradeService.deleteTrade(findTradeByIdService.findTradeByTradeId(tradeid));
			return ResponseEntity.ok(new TradeDTO());
			
		}
		
		
	}
	
}
