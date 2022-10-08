package com.auth2.oseidclient.curvepoint.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
		curvePoint.setCurveId(1);
		saveCurvePointService.saveCurvePoint(curvePoint);
		
		verify(curvePointRepository, times(1)).save(curvePoint);
		
	}
	
}
