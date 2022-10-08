package com.auth2.oseidclient.curvepoint.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.repository.CurvePointRepository;

@Service
public class FindCurvePointByIdService {

	private static final Logger LOGGER = LogManager.getLogger("FindCurvePointByIdService");
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	FindCurvePointByIdService(CurvePointRepository curvePointRepository
			){
		this.curvePointRepository = curvePointRepository;
	}
	
	public CurvePoint findCurvePointById(Integer id) {
		
		LOGGER.info("looking for curvepoint: "+id);
		CurvePoint curve = new CurvePoint();
		int count = 0;
		
		if(curvePointRepository.findAll().isEmpty()) {
			LOGGER.info("No curvepoint registered");

		}else {
			LOGGER.info("Curvepoint found");
			for(CurvePoint p: curvePointRepository.findAll()) {
				if(Objects.equals(p.getId(), id)) {
					curve = p;
					count++;
				}
			}
			
		}
		
		if(count == 0) {
			curve.setId(-1);
		}
		
		return curve;
	}

}
