package com.auth2.oseidclient.restcontroller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.DTO.TradeDTO;
import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.service.TradeService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class TradeRestController {

	public static final Logger LOGGER = LogManager.getLogger("TradeRestController");
	
	@Autowired
	private TradeService tradeService;
	
	TradeRestController(TradeService tradeService){
		this.tradeService = tradeService;
	}
	
	@GetMapping("/trade")//?account=<account>
	public Iterable<TradeDTO> getTrade(@RequestParam String account){
		List<TradeDTO> tradeDtoList = new ArrayList<TradeDTO>();
		
		if(tradeService.findTradeByAccount(account).get(0).getTradeId() == -1) {
			LOGGER.info("No trade found for account: "+account);
			
		}else {
			LOGGER.info("Trade found for account: "+account);
			TradeDTO tradeDto = new TradeDTO();
			tradeDto.setAccount(account);
			for(Trade t : tradeService.findTradeByAccount(account)) {
				tradeDto.setTradeId(t.getTradeId());
				tradeDto.setType(t.getType());
				LOGGER.info("Trade added to display list: "+tradeDto);
				tradeDtoList.add((TradeDTO)tradeDto.clone());
				
			}
			
		}
		return tradeDtoList;
		
	}
	
	@PostMapping("/trade")
	public ResponseEntity<Trade> addTrade(@RequestBody Optional<@Valid Trade> tradeDtoOptional){
		
		if(tradeDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, RequestBody is empty");
			return ResponseEntity.badRequest().build();
		}else {
			Trade tradeDto = tradeDtoOptional.get();
			LOGGER.info("New Trade: "+tradeDto);
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<Trade>> violations = validator.validate(tradeDto);
			LOGGER.info("VALIDATION"+validator.validate(tradeDto));
			LOGGER.info("VIOLATION "+violations.size());

			if(violations.size()>0) {
				LOGGER.info("Bad request, constraint violation: "+violations);
				return ResponseEntity.badRequest().build();
			}else {
				LOGGER.info("Processing trade creation");
				Trade tradeToAdd = new Trade();
				
				tradeToAdd.setAccount(tradeDto.getAccount());
				tradeToAdd.setType(tradeDto.getType());
				
				LOGGER.info("passing to saveTrade service");
				Integer savedId = tradeService.saveTrade(tradeToAdd);
				tradeDto.setTradeId(savedId);
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/trade")
						.buildAndExpand("?account="+savedId).toUri();
				
				LOGGER.info("URI created");
				return ResponseEntity.created(location).body(tradeDto);
				
			}
		
		}
		
	}
	
	@PutMapping("/trade")//?tradeid=<tradeid>
	public ResponseEntity<TradeDTO> updateTrade(@RequestBody Optional<TradeDTO> tradeDtoOptional, @RequestParam Integer tradeid){
		
		if(tradeDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, RequestBody is empty");
			return ResponseEntity.badRequest().build();			
		}else {
			LOGGER.info("Request, is being processed");
			TradeDTO tradeDto = tradeDtoOptional.get();
			
			if(tradeService.findTradeByTradeId(tradeid).getTradeId() == -1){
				LOGGER.info("No registered trade with Id: "+tradeDto.getTradeId());
				return ResponseEntity.notFound().build();			
				
			}else {
				LOGGER.info("UpdatingTrade with Id: "+tradeDto.getTradeId());
			Trade tradeToUpdate = new Trade();
			
			tradeToUpdate.setAccount(tradeDto.getAccount());
			tradeToUpdate.setType(tradeDto.getType());
			
			LOGGER.info("loading into database");
			tradeService.saveTrade(tradeToUpdate);
			
			return  ResponseEntity.ok(tradeDto);
			
			}
			
		}
		
	}
	
	@DeleteMapping("/trade")//?tradeid=<tradeid>
	public ResponseEntity<TradeDTO> deleteTrade(@RequestParam Integer tradeid){
		
		LOGGER.info("Looking for trade with Id: "+tradeid);
		if(tradeService.findTradeByTradeId(tradeid).getTradeId() == -1) {
			LOGGER.info("Id: "+tradeid+", not registered");
			return ResponseEntity.notFound().build();
			
		}else {
			LOGGER.info("Deleting trade with Id: "+tradeid);
			tradeService.deleteTrade(tradeService.findTradeByTradeId(tradeid));
			return ResponseEntity.ok(new TradeDTO());
			
		}
		
		
	}
	
}
