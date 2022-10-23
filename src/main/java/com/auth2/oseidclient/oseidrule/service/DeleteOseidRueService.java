package com.auth2.oseidclient.oseidrule.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidLeruRepository;

@Service
public class DeleteOseidRueService {

	private static final Logger LOGGER = LogManager.getLogger("DeleteOseidRueService");

	@Autowired
	private OseidLeruRepository oseidLeruRepository;
	
	DeleteOseidRueService(OseidLeruRepository oseidLeruRepository
			){
		this.oseidLeruRepository = oseidLeruRepository;
	}
	
	public void deleteOseidRue(OseidLeru rule) {
		LOGGER.info("Deleting rule "+rule.getId());
		oseidLeruRepository.delete(rule);
		
	}
	
}
