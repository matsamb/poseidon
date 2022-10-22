package com.auth2.oseidclient.restcontroller;

import java.net.URI;
import java.util.Objects;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.service.CurvePointService;

@RestController
@RolesAllowed({"ADMIN","USER"})
public class CurvePointRestController {

	private static final Logger LOGGER = LogManager.getLogger("CurvePointRestController");

	@Autowired
	private CurvePointService curvePointService;
	
	CurvePointRestController(CurvePointService curvePointService){
		this.curvePointService = curvePointService;
	}
	
	@GetMapping("/curvepoint")//?id=<id> 
	public CurvePoint getCurvePoint(@RequestParam Integer id) {
		
		CurvePoint curve = curvePointService.findCurvePointById(id);
		LOGGER.info("curvepoint");
		if(Objects.equals(curve.getId(), id)) {
			LOGGER.info("Found curvepoint with id: "+curve.getId());
			CurvePoint curveDto = new CurvePoint();
			curveDto.setId(curve.getId());
			curveDto.setCurveId(curve.getCurveId());
			curveDto.setTerm(curve.getTerm());
			curveDto.setValue(curve.getValue());
			LOGGER.info("displaying curvepoint: "+curveDto);
			return curveDto;
		}
		return new CurvePoint();
	}
	
	@PostMapping("curvepoint")
	public ResponseEntity<CurvePoint> addCurvePoint(@RequestBody Optional<CurvePoint> curvePointDtoOptional){
		
		if(curvePointDtoOptional.isEmpty()) {
			LOGGER.info("Bad request, empty request body");
			return ResponseEntity.badRequest().build();		
		}else {
			LOGGER.info("processing request");
			CurvePoint curvePointDto = curvePointDtoOptional.get();
			
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePointDto);
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
				
				Integer savedCurveId = curvePointService.saveCurvePoint(newCurvePoint);
				newCurvePoint.setId(savedCurveId);
				URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/curvepoint")
						.buildAndExpand("?id="+savedCurveId)
						.toUri()
						;
				
				return ResponseEntity.created(location).body(newCurvePoint);
			}
			
		}
		
	}
	
	@PutMapping("/curvepoint") // ?id=<id>
	public ResponseEntity<CurvePoint> updateCurvePoint(@RequestBody Optional<CurvePoint> curvePointOptional,
			@RequestParam Integer id) {

		LOGGER.info("Curvepoint: "+id);
		
		if (curvePointOptional.isEmpty()) {
			LOGGER.info("Bad request, RequestBody is empty");
			return ResponseEntity.badRequest().build();
		} else {

			if (curvePointService.findCurvePointById(id).getId() == -1) {
				LOGGER.info("Curvepoint with id: " + id + ", not found");
				return ResponseEntity.notFound().build();

			} else {
				LOGGER.info("processing update");
				CurvePoint curvePoint = curvePointOptional.get();

				Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
				Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);

				if (violations.size() > 0) {
					LOGGER.info("Bad request, constraint violations: " + violations);
					return ResponseEntity.badRequest().build();

				} else {
					LOGGER.info("Updating curvepoint with id: "+id);
					CurvePoint curvePointToUpdate = new CurvePoint();
					
					curvePointToUpdate.setId(id);
					curvePointToUpdate.setCurveId(curvePoint.getCurveId());
					curvePointToUpdate.setTerm(curvePoint.getTerm());
					curvePointToUpdate.setValue(curvePoint.getValue());
					LOGGER.info("Loading curvepoint into database");
					
					curvePointService.saveCurvePoint(curvePointToUpdate);
					
					return ResponseEntity.ok(curvePoint);
					
				}
			}
		}
	}
	
	@DeleteMapping("/curvepoint")//?id=<id>
	public ResponseEntity<CurvePoint> deleteCurvePoint(@RequestParam Integer id){
		 
		if(curvePointService.findCurvePointById(id).getId() == -1) {
			LOGGER.info("CurvePoint with id: "+id+", not found");
			return ResponseEntity.notFound().build();
		}else {
			LOGGER.info("Found and deleting curvePoint with id: "+id);
			CurvePoint curvePointToDelete = curvePointService.findCurvePointById(id);
			curvePointService.deleteCurvePoint(curvePointToDelete);
			return ResponseEntity.ok(new CurvePoint());
		}
		
	}
	
}
