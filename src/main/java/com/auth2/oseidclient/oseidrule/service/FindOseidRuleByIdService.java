package com.auth2.oseidclient.oseidrule.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidLeruRepository;

@Service
public class FindOseidRuleByIdService {

	private static final Logger LOGGER = LogManager.getLogger("FindOseidRuleServiceById");

	@Autowired
	private OseidLeruRepository oseidLeruRepository;
	
	public FindOseidRuleByIdService(OseidLeruRepository oseidLeruRepository){
		this.oseidLeruRepository = oseidLeruRepository;
	}

	public OseidLeru findOseidRuleById(Integer id) {
		LOGGER.always();
		OseidLeru r = new OseidLeru();
		if(oseidLeruRepository.findAll().isEmpty()) {
			LOGGER.info("No rule registered with id: "+id);	
			
		}else {

			LOGGER.info("Found Rules");
			for(OseidLeru o : oseidLeruRepository.findAll()) {
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
