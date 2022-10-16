package com.auth2.oseidclient.oseidrule.restcontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.oseidrule.service.DeleteOseidRueService;
import com.auth2.oseidclient.oseidrule.service.FindOseidRuleByIdService;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@RestController
public class DeleteOseidRuRestController {

	private static final Logger LOGGER = LogManager.getLogger("DeleteOseidRueRestController");

	@Autowired
	private FindOseidRuleByIdService findOseidRuleByIdService;
	
	@Autowired
	private DeleteOseidRueService deleteOseidRueService;
	
	@Autowired
	private OseidRuleRepository oseidRuleRepository;

	DeleteOseidRuRestController(FindOseidRuleByIdService findOseidRuleByIdService
			, DeleteOseidRueService deleteOseidRueService
			){
		this.findOseidRuleByIdService = findOseidRuleByIdService;
		this.deleteOseidRueService = deleteOseidRueService;
	}
	
	@DeleteMapping("/rue")
	private ResponseEntity<OseidLeru> deleteRule(@RequestParam Integer id){
		LOGGER.always();
		FindOseidRuleByIdService findOseidRuleByIdService = new FindOseidRuleByIdService(oseidRuleRepository);
		LOGGER.info(findOseidRuleByIdService.findOseidRuleById(id));
		
/*		if(findOseidRuleByIdService.findOseidRuleById(id).getId() == -1) {
			LOGGER.info("No rule match for id: "+id);
			return ResponseEntity.notFound().build();
		}else {*/
			LOGGER.info("rule: "+id+", found");
			deleteOseidRueService.deleteOseidRue(findOseidRuleByIdService.findOseidRuleById(id));
			return ResponseEntity.ok(new OseidLeru());
		//}

		
	}
}
