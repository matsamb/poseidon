package com.auth2.oseidclient.oseidrule.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidRuleRepository;

@Service
public class FindOseidRuleByIdService {

	private static final Logger LOGGER = LogManager.getLogger("FindOseidRuleServiceById");

	@Autowired
	private OseidRuleRepository oseidRuleRepository;
	
	public FindOseidRuleByIdService(OseidRuleRepository oseidRuleRepository){
		this.oseidRuleRepository = oseidRuleRepository;
	}

	public OseidLeru findOseidRuleById(Integer id) {
		LOGGER.always();
		OseidLeru r = new OseidLeru();
		if(oseidRuleRepository.findAll().isEmpty()) {
			LOGGER.info("No rule registered with id: "+id);	
			
		}else {

			LOGGER.info("Found Rules");
			for(OseidLeru o : oseidRuleRepository.findAll()) {
				if(Objects.equals(o.getId(),id)) {
					LOGGER.info("Returning rule with id: "+id);
					return o;
				}
			}
			
		}
		r.setId(-1);
		return r;
	}
	
	
	
}
