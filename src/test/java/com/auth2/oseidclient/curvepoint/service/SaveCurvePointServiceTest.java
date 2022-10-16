package com.auth2.oseidclient.curvepoint.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.repository.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class SaveCurvePointServiceTest {

	@Mock
	private CurvePointRepository curvePointRepository;
	
	@InjectMocks
	private SaveCurvePointService saveCurvePointService;
	
	@Test
	public void test() {
		
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setValue(1.0);
		
		CurvePoint curvePointWithId = new CurvePoint();
		curvePointWithId.setId(1);
		curvePointWithId.setValue(1.0);
		
		when(curvePointRepository.saveAndFlush(curvePoint)).thenReturn(curvePointWithId);
		
		Integer returnedId = saveCurvePointService.saveCurvePoint(curvePoint);
		
		assertThat(returnedId).isEqualTo(curvePointWithId.getId());
		
	}
	
}
