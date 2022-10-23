package com.auth2.oseidclient.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.OseidLeru;
import com.auth2.oseidclient.repository.OseidLeruRepository;

@Service
public class OseidLeruService {

	private static final Logger LOGGER = LogManager.getLogger("OseidLeruRestController");

	@Autowired
	private OseidLeruRepository oseidLeruRepository;
	
	public OseidLeruService(OseidLeruRepository oseidLeruRepository){
		this.oseidLeruRepository = oseidLeruRepository;
	}
	
	public Integer saveOseidLeru(OseidLeru leruToAdd) {
		LOGGER.info("Saved leru id: "+oseidLeruRepository.saveAndFlush(leruToAdd).getId());
		Integer Id = oseidLeruRepository.saveAndFlush(leruToAdd).getId();
		return Id;
	}
	
	public OseidLeru findOseidLeruById(Integer id) {
		LOGGER.always();
		OseidLeru r = new OseidLeru();
		if(oseidLeruRepository.findAll().isEmpty()) {
			LOGGER.info("No leru registered with id: "+id);	
			
		}else {

			LOGGER.info("Found Lerus");
			for(OseidLeru o : oseidLeruRepository.findAll()) {
				if(Objects.equals(o.getId(),id)) {
					LOGGER.info("Returning leru with id: "+id);
					return o;
				}
			}
			
		}
		r.setId(-1);
		return r;
	}

	public boolean deleteOseidLeru(Integer id) {
		LOGGER.info("Deleting leru "+id);
		if(oseidLeruRepository.findById(id).isPresent()) {
			oseidLeruRepository.deleteById(id);
			return true;
		};	
		return false;
	}
	
}
