package com.auth2.oseidclient.oseidrule.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class GetOseidRuleRestController {

	private static final Logger LOGGER = LogManager.getLogger("GetOseidRuleRestController");

	@Autowired
	private FindOseidRuleByIdService findOseidRuleByIdService;

	GetOseidRuleRestController(FindOseidRuleByIdService findOseidRuleByIdService){
		this.findOseidRuleByIdService = findOseidRuleByIdService;
	}
	
//	@GetMapping("/rule")//?id=<id>
	public ResponseEntity<OseidLeru> getOseidRule(@RequestParam Integer id){
		LOGGER.always();
		
		if(findOseidRuleByIdService.findOseidRuleById(id).getId() == -1) {
			LOGGER.info("No registered rule with id: "+id);
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("Rule: "+id+", available");
			return ResponseEntity.ok(findOseidRuleByIdService.findOseidRuleById(id));
		}
		
	}
	
	
}
