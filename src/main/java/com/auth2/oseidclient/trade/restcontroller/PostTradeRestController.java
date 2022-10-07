package com.auth2.oseidclient.trade.restcontroller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.DTO.TradeDTO;
import com.auth2.oseidclient.entity.Trade;
import com.auth2.oseidclient.trade.service.SaveTradeService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PostTradeRestController {

	public static final Logger LOGGER = LogManager.getLogger("PostTradeRestController");
	
	@Autowired
	private SaveTradeService saveTradeService;
	
	PostTradeRestController(SaveTradeService saveTradeService
			
			){
		this.saveTradeService = saveTradeService;
	}
	
	@PostMapping("/trade")
	public ResponseEntity<TradeDTO> addTrade(@RequestBody Optional<@Valid TradeDTO> tradeDtoOptional){
		
		if(tradeDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, RequestBody is empty");
			return ResponseEntity.badRequest().build();
		}else {
			TradeDTO tradeDto = tradeDtoOptional.get();
			LOGGER.info("New Trade: "+tradeDto);
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<TradeDTO>> violations = validator.validate(tradeDto);
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
				saveTradeService.saveTrade(tradeToAdd);
				
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/trade")
						.buildAndExpand("?account="+tradeToAdd.getAccount()).toUri();
				
				LOGGER.info("URI created");
				return ResponseEntity.created(location).build();
				
			}
		
		}
		
	}
	
}
