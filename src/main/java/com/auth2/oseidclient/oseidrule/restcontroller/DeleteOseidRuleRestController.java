package com.auth2.oseidclient.oseidrule.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.oseidrule.service.DeleteOseidRuleService;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class DeleteOseidRuleRestController {

	private static final Logger LOGGER = LogManager.getLogger("DeleteOseidRuleRestController");

	@Autowired
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@Autowired
	private DeleteOseidRuleService deleteOseidRuleService;
	
	DeleteOseidRuleRestController(FindOseidRuleByIdService findOseidRuleByIdService
			, DeleteOseidRuleService deleteOseidRuleService
			){
		this.findOseidRuleByIdService = findOseidRuleByIdService;
		this.deleteOseidRuleService = deleteOseidRuleService;
	}
	
	@DeleteMapping("/rule")
	private ResponseEntity<OseidRule> deleteRule(@RequestParam Integer id){
		LOGGER.always();
		LOGGER.info(findOseidRuleByIdService.findOseidRuleById(id));
		
		if(findOseidRuleByIdService.findOseidRuleById(id).getId() == -1) {
			LOGGER.info("No rule match for id: "+id);
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("rule: "+id+", found");
			deleteOseidRuleService.deleteRule(findOseidRuleByIdService.findOseidRuleById(id));
			return ResponseEntity.ok(new OseidRule());
		}
		
		
	}
	
}
