package com.auth2.oseidclient.curvepoint.restcontroller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth2.oseidclient.DTO.CurvePointDTO;
import com.auth2.oseidclient.curvepoint.service.FindCurvePointByIdService;
import com.auth2.oseidclient.curvepoint.service.SaveCurvePointService;
import com.auth2.oseidclient.entity.CurvePoint;

@RestController
@RolesAllowed({ "ADMIN", "USER" })
public class PutCurvePointRestController {

	private static final Logger LOGGER = LogManager.getLogger("PutCurvePointRestController");

	@Autowired
	private SaveCurvePointService saveCurvePointService;

	@Autowired
	private FindCurvePointByIdService findCurvePointByIdService;

	PutCurvePointRestController(SaveCurvePointService saveCurvePointService,
			FindCurvePointByIdService findCurvePointByIdService) {
		this.findCurvePointByIdService = findCurvePointByIdService;
		this.saveCurvePointService = saveCurvePointService;
	}

//	@PutMapping("/curvepoint") // ?id=<id>
	public ResponseEntity<CurvePoint> updateCurvePoint(@RequestBody Optional<CurvePoint> curvePointOptional,
			@RequestParam Integer id) {

		LOGGER.info("Curvepoint: "+id);
		
		if (curvePointOptional.isEmpty()) {
			LOGGER.info("Bad request, RequestBody is empty");
			return ResponseEntity.badRequest().build();
		} else {

			if (findCurvePointByIdService.findCurvePointById(id).getId() == -1) {
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
					
					saveCurvePointService.saveCurvePoint(curvePointToUpdate);
					
					return ResponseEntity.ok(curvePoint);
					
				}
			}
		}
	}
}
