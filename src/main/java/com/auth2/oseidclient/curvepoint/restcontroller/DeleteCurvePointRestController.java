package com.auth2.oseidclient.curvepoint.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.CurvePointDTO;
import com.auth2.oseidclient.curvepoint.service.DeleteCurvePointService;
import com.auth2.oseidclient.curvepoint.service.FindCurvePointByIdService;
import com.auth2.oseidclient.entity.CurvePoint;

import lombok.extern.java.Log;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class DeleteCurvePointRestController {

	private static final Logger LOGGER = LogManager.getLogger("DeleteCurvePointRestController");
	
	@Autowired
	private DeleteCurvePointService deleteCurvePointService;
	
	@Autowired
	private FindCurvePointByIdService findCurvePointByIdService;
	
	DeleteCurvePointRestController(DeleteCurvePointService deleteCurvePointService
			, FindCurvePointByIdService findCurvePointByIdService
			){
		this.deleteCurvePointService = deleteCurvePointService;
		this.findCurvePointByIdService = findCurvePointByIdService;
	}
	
//	@DeleteMapping("/curvepoint")//?id=<id>
	public ResponseEntity<CurvePoint> deleteCurvePoint(@RequestParam Integer id){
		
		if(findCurvePointByIdService.findCurvePointById(id).getId() == -1) {
			LOGGER.info("CurvePoint with id: "+id+", not found");
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("Found and deleting curvePoint with id: "+id);
			CurvePoint curvePointToDelete = findCurvePointByIdService.findCurvePointById(id);
			deleteCurvePointService.deleteCurvePoint(curvePointToDelete);
			return ResponseEntity.ok(new CurvePoint());
		}
		
	}
	
}
