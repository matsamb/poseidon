package com.auth2.oseidclient.oseidrule.restcontroller;

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

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.oseidrule.service.SaveOseidRuleService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PostOseidRuleRestController {

	private static final Logger LOGGER = LogManager.getLogger("PostOseidRuleRestController");
	
	@Autowired
	private SaveOseidRuleService saveOseidRuleService;
	
	PostOseidRuleRestController(SaveOseidRuleService saveOseidRuleService
			){
		this.saveOseidRuleService = saveOseidRuleService;
	}
	
	@PostMapping("/rule")
	public ResponseEntity<OseidRule> addRule(@RequestBody Optional<@Valid OseidRule> ruleDtoOptional){
		
		if(ruleDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
			
		}else {
			LOGGER.info("processing request");
			OseidRule ruleDto = ruleDtoOptional.get();
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<OseidRule>> violations = validator.validate(ruleDto);
			
			if(violations.size() > 0) {
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();
				
			}else {
				
				OseidRule ruleToAdd = new OseidRule();
				
//				ruleToAdd.setId(ruleDto.getId());
				ruleToAdd.setName(ruleDto.getName());
				ruleToAdd.setDescription(ruleDto.getDescription());
				
				saveOseidRuleService.saveOseidRule(ruleToAdd);
				
/*				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/rule")
						.buildAndExpand("?Id="+ruleToAdd.getId()).toUri();*/
				
				return ResponseEntity.ok(ruleToAdd);//ResponseEntity.created(location).body(ruleDto);
				
			}
			
		}
		
		
	}
	
}
