package com.auth2.oseidclient.curvepoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.repository.CurvePointRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class SaveCurvePointService {

	public static final Logger LOGGER = LogManager.getLogger("SaveCurvePointService");
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	SaveCurvePointService(CurvePointRepository curvePointRepository
			){
		this.curvePointRepository = curvePointRepository;
	}
	
	public void saveCurvePoint(CurvePoint newCurvePoint) {
		LOGGER.info("loading curvepoint: "+newCurvePoint);
		curvePointRepository.save(newCurvePoint);
	}

}
