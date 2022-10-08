package com.auth2.oseidclient.curvepoint.restcontroller;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.DTO.CurvePointDTO;
import com.auth2.oseidclient.curvepoint.service.SaveCurvePointService;
import com.auth2.oseidclient.entity.CurvePoint;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class PostCurvePointRestController {

	public static final Logger LOGGER = LogManager.getLogger("PostCurvePointRestController");
	
	@Autowired
	private SaveCurvePointService saveCurvePointService;
	
	PostCurvePointRestController(SaveCurvePointService saveCurvePointService
			){
		this.saveCurvePointService = saveCurvePointService;
	}
	
	@PostMapping("curvepoint")
	public ResponseEntity<CurvePointDTO> addCurvePoint(@RequestBody Optional<CurvePointDTO> curvePointDtoOptional){
		
		if(curvePointDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();		
		}else {
			LOGGER.info("processing request");
			CurvePointDTO curvePointDto = curvePointDtoOptional.get();
			
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<CurvePointDTO>> violations = validator.validate(curvePointDto);
			LOGGER.info("VALIDATION"+validator.validate(curvePointDto));
			LOGGER.info("VIOLATION "+violations.size());
			
			if(violations.size()>0) {
				LOGGER.info("Bad request, constraint violation: "+violations);
				return ResponseEntity.badRequest().build();
			}else {
				LOGGER.info("Creating curve point: "+curvePointDto);
			
				CurvePoint newCurvePoint = new CurvePoint();
				newCurvePoint.setCurveId(curvePointDto.getCurveId());
				newCurvePoint.setTerm(curvePointDto.getTerm());
				newCurvePoint.setValue(curvePointDto.getValue());
				LOGGER.info("CurvePoint set");
				
				
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/curvepoint")
						.buildAndExpand("?id="+newCurvePoint.getId())
						.toUri()
						;
				saveCurvePointService.saveCurvePoint(newCurvePoint);
				
				return ResponseEntity.created(location).build();
			}
			
		}
		
	}
	
}
