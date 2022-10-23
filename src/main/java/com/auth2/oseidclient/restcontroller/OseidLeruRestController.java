package com.auth2.oseidclient.restcontroller;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
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

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.service.OseidLeruService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class OseidLeruRestController {

	private static final Logger LOGGER = LogManager.getLogger("OseidLeruRestController");

	@Autowired
	private OseidLeruService oseidLeruService;
	
	OseidLeruRestController(OseidLeruService oseidLeruService){
		this.oseidLeruService = oseidLeruService;
	}
	
	@GetMapping("/leru")//?id=<id>
	public ResponseEntity<OseidLeru> getOseidRule(@RequestParam Integer id){
		LOGGER.always();
		
		if(oseidLeruService.findOseidLeruById(id).getId() == -1) {
			LOGGER.info("No registered rule with id: "+id);
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("Rule: "+id+", available");
			return ResponseEntity.ok(oseidLeruService.findOseidLeruById(id));
		}
		
	}
	
	@PostMapping("/leru")
	public ResponseEntity<OseidLeru> addLeru(@RequestBody Optional<@Valid OseidLeru> leruDtoOptional){
		
		if(leruDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, request body is empty");
			return ResponseEntity.badRequest().build();
			
		}else {
			LOGGER.info("processing request");
			OseidLeru leruDto = leruDtoOptional.get();
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<OseidLeru>> violations = validator.validate(leruDto);
			
			if(violations.size() > 0) {
				LOGGER.info("Bad request, constraint violations: "+violations);
				return ResponseEntity.badRequest().build();
				
			}else {
				LOGGER.info("creating leru");
				Integer addedLeruId = oseidLeruService.saveOseidLeru(leruDto);
				LOGGER.info("created leru id: "+addedLeruId/*.getId()*/);
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/leru")
						.buildAndExpand("?id="+addedLeruId).toUri();
				leruDto.setId(addedLeruId);		
				return ResponseEntity.created(location).body(leruDto);
				
			}	
		}
	}

	@PutMapping("/leru")//?id=<id>
	public ResponseEntity<OseidLeru> updateRule(@RequestBody Optional<OseidLeru> ruleDtoOptional, @RequestParam Integer id){

		LOGGER.always();
		if(ruleDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();
		}else {
			if (oseidLeruService.findOseidLeruById(id).getId() == -1) {

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

				oseidLeruService.saveOseidLeru(ruleDto);
				
				return ResponseEntity.ok(ruleDto);
			}
			}	
		}
		
	}	
	
/*	//TODO
	
//	@DeleteMapping("/leru")
	private ResponseEntity<OseidLeru> deleteLeru(@RequestParam Integer id){
		LOGGER.info("delete leru");
			if(oseidLeruService.deleteOseidLeru(id)) {
				return ResponseEntity.ok(new OseidLeru());
			}
			return ResponseEntity.notFound().build();
		
	}*/
	
}
