package com.auth2.oseidclient.curvepoint.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.repository.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class FindCurvePointByIdServiceTest {

	@Mock
	private CurvePointRepository curvePointRepository;
	
	@InjectMocks
	private FindCurvePointByIdService findCurvePointByIdService;
	
	@Test
	public void givenRegisteredCurve1_whenFindCurvePointByIdServiceCalled_thenItShouldReturnCurve1 () {
		
		CurvePoint curve1 = new CurvePoint();
		curve1.setId(1);
		CurvePoint curve2 = new CurvePoint();
		curve2.setId(2);
		
		List<CurvePoint> curveList = new ArrayList<>();
		curveList.add(curve1);
		curveList.add(curve2);
		
		when(curvePointRepository.findAll()).thenReturn(curveList);
	
		CurvePoint resultCurve = findCurvePointByIdService.findCurvePointById(1);
		
		assertThat(resultCurve).isEqualTo(curve1);
	}
	
	@Test
	public void givenNotRegisteredCurve3_whenFindCurvePointByIdServiceCalled_thenItShouldReturnDefaultNotRegisteredCurve () {
		
		CurvePoint curve1 = new CurvePoint();
		curve1.setId(1);
		CurvePoint curve2 = new CurvePoint();
		curve2.setId(2);
		
		CurvePoint defaultNotRegistered = new CurvePoint();
		defaultNotRegistered.setId(-1);
		
		List<CurvePoint> curveList = new ArrayList<>();
		curveList.add(curve1);
		curveList.add(curve2);
		
		when(curvePointRepository.findAll()).thenReturn(curveList);
	
		CurvePoint resultCurve = findCurvePointByIdService.findCurvePointById(3);
		
		assertThat(resultCurve).isEqualTo(defaultNotRegistered);
	}
	
	@Test
	public void givenNoRegisteredCurve_whenFindCurvePointByIdServiceCalled_thenItShouldReturnDefaultNotRegisteredCurve () {

		CurvePoint defaultNotRegistered = new CurvePoint();
		defaultNotRegistered.setId(-1);
		
		List<CurvePoint> curveList = new ArrayList<>();
		
		when(curvePointRepository.findAll()).thenReturn(curveList);
	
		CurvePoint resultCurve = findCurvePointByIdService.findCurvePointById(1);
		
		assertThat(resultCurve).isEqualTo(defaultNotRegistered);
	}
	
}
