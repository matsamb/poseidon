package com.auth2.oseidclient.curvepoint.restcontroller;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.CurvePointDTO;
import com.auth2.oseidclient.curvepoint.service.FindCurvePointByIdService;
import com.auth2.oseidclient.entity.CurvePoint;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class GetCurvePointRestController {

	private static final Logger LOGGER = LogManager.getLogger("FindCurvePointByIdService");

	@Autowired
	private FindCurvePointByIdService findCurvePointByIdService;
	
	GetCurvePointRestController(FindCurvePointByIdService findCurvePointByIdService
			){
		this.findCurvePointByIdService = findCurvePointByIdService;
	}
	
	@GetMapping("/curvepoint")//?id=<id>
	public CurvePointDTO getCurvePoint(@RequestParam Integer id) {
		
		CurvePoint curve = findCurvePointByIdService.findCurvePointById(id);
		LOGGER.info("curvepoint");
		if(Objects.equals(curve.getId(), id)) {
			LOGGER.info("Found curvepoint with id: "+curve.getId());
			CurvePointDTO curveDto = new CurvePointDTO();
			curveDto.setId(curve.getId());
			curveDto.setCurveId(curve.getCurveId());
			curveDto.setTerm(curve.getTerm());
			curveDto.setValue(curve.getValue());
			LOGGER.info("displaying curvepoint: "+curveDto);
			return curveDto;
		}
		return null;
	}
	
}
