package com.auth2.oseidclient.oseidrule.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@Service
@Transactional
public class SaveOseidRuleService {

	private static final Logger LOGGER = LogManager.getLogger("SaveOseidRuleService");

	@Autowired
	private OseidRuleRepository oseidRuleRepository;
	
	SaveOseidRuleService(OseidRuleRepository oseidRuleRepository
			){
		this.oseidRuleRepository = oseidRuleRepository;
	}

	public Integer saveOseidRule(OseidLeru ruleToAdd) {
		LOGGER.info("Saved rule id: "+oseidRuleRepository.saveAndFlush(ruleToAdd).getId());
		Integer Id = oseidRuleRepository.saveAndFlush(ruleToAdd).getId();
		return Id;
	}
	
}
