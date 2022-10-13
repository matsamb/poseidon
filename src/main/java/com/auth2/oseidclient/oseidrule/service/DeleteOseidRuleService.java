package com.auth2.oseidclient.oseidrule.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidRule;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@Service
public class DeleteOseidRuleService {

	private static final Logger LOGGER = LogManager.getLogger("DeleteOseidRuleService");

	@Autowired
	private OseidRuleRepository oseidRuleRepository;
	
	DeleteOseidRuleService(OseidRuleRepository oseidRuleRepository
			){
		this.oseidRuleRepository = oseidRuleRepository;
	}

	public void deleteRule(OseidRule rule) {
		LOGGER.info("Deleting rule "+rule.getId());
		oseidRuleRepository.delete(rule);
		
	}
	
	
	
}
