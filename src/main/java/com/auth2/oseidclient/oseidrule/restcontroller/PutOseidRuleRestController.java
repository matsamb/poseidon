package com.auth2.oseidclient.oseidrule.restcontroller;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;
import com.auth2.oseidclient.oseidrule.service.SaveOseidRuleService;

@RestController
public class PutOseidRuleRestController {

	private static final Logger LOGGER = LogManager.getLogger("PutOseidRuleRestController");

	@Autowired
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@Autowired
	private SaveOseidRuleService saveOseidRuleService;
	
	PutOseidRuleRestController(FindOseidRuleByIdService findOseidRuleByIdService
			, SaveOseidRuleService saveOseidRuleService
			){
		this.findOseidRuleByIdService = findOseidRuleByIdService;
		this.saveOseidRuleService = saveOseidRuleService;
	}
	
//	@PutMapping("/rule")//?id=<id>
	public ResponseEntity<OseidLeru> updateRule(@RequestBody Optional<OseidLeru> ruleDtoOptional, @RequestParam Integer id){

		LOGGER.always();
		if(ruleDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();
		}else {
			if (findOseidRuleByIdService.findOseidRuleById(id).getId() == -1) {

				LOGGER.info("No rule match for id: "+id);
				return ResponseEntity.notFound().build();
			}else {
			
			LOGGER.always();
			OseidLeru ruleDto = ruleDtoOptional.get();
			
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<OseidLeru>> violations = validator.validate(ruleDto);
			
			if(violations.size() > 0) {
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();
			}else {
				LOGGER.info("updating rule "+id);

				saveOseidRuleService.saveOseidRule(ruleDto);
				
				return ResponseEntity.ok(ruleDto);
			}
			}	
		}
		
	}
	
}
