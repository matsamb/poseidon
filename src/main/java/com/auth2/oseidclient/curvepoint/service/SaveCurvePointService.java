package com.auth2.oseidclient.curvepoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.repository.CurvePointRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@Transactional
public class SaveCurvePointService {

	public static final Logger LOGGER = LogManager.getLogger("SaveCurvePointService");
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	SaveCurvePointService(CurvePointRepository curvePointRepository
			){
		this.curvePointRepository = curvePointRepository;
	}
	
	public Integer saveCurvePoint(CurvePoint newCurvePoint) {
		LOGGER.info("saved curvepoint id: "+curvePointRepository.saveAndFlush(newCurvePoint).getId());
		Integer returnedId = curvePointRepository.saveAndFlush(newCurvePoint).getId();
		return returnedId;
	}

}
