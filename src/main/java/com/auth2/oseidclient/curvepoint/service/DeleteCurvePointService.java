package com.auth2.oseidclient.curvepoint.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.repository.CurvePointRepository;

@Service
@Transactional
public class DeleteCurvePointService {

	private static final Logger LOGGER = LogManager.getLogger("DeleteCurvePointService");
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	DeleteCurvePointService(CurvePointRepository curvePointRepository
			){
		this.curvePointRepository = curvePointRepository;
	}

	public void deleteCurvePoint(CurvePoint curvePointToDelete) {
		LOGGER.info("Deleting curvpoint: "+curvePointToDelete.getId());
		curvePointRepository.delete(curvePointToDelete);
	}
	
	
	
}
