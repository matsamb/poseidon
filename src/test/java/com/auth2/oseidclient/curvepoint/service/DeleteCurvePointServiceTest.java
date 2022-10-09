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
public class DeleteCurvePointServiceTest {

	@Mock
	private CurvePointRepository curvePointRepository;
	
	@InjectMocks
	private DeleteCurvePointService deleteCurvePointService;
	
	@Test
	public void givenCurvePoint1_whenDeleteServiceCalled_thenCurvePointRepositoryDeleteMethodeShouldBeCalledOnce() {
		
		CurvePoint curve = new CurvePoint();
		curve.setId(1);
		
		deleteCurvePointService.deleteCurvePoint(curve);
		
		verify(curvePointRepository, times(1)).delete(curve);
		
	}
	
}
