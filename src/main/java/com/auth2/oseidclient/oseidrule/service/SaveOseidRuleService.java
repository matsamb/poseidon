package com.auth2.oseidclient.oseidrule.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@Service
@Transactional
public class SaveOseidRuleService {

	private static final Logger LOGGER = LogManager.getLogger("SaveOseidRuleService");

	@Autowired
	private OseidRuleRepository oseidRuleService;
	
	SaveOseidRuleService(OseidRuleRepository oseidRuleService
			){
		this.oseidRuleService = oseidRuleService;
	}

	public void saveOseidRule(OseidRule ruleToAdd) {
		LOGGER.info("loading rule into database: "+ruleToAdd);
		oseidRuleService.save(ruleToAdd);
	}
	
}
