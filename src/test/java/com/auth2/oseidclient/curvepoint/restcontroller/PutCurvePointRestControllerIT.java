package com.auth2.oseidclient.curvepoint.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.curvepoint.service.FindCurvePointByIdService;
import com.auth2.oseidclient.curvepoint.service.SaveCurvePointService;
import com.auth2.oseidclient.entity.CurvePoint;

@SpringBootTest
@AutoConfigureMockMvc
public class PutCurvePointRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SaveCurvePointService saveCurvePointService;

	@MockBean
	private FindCurvePointByIdService findCurvePointByIdService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(put("/curvepoint"))
					.apply(springSecurity())
					.build()
					;
		
	}

	@Test
	public void givenRegisteredCurvepoint1_whenPutCurvepointCalled_thenItShouldReturnStatusIsOk() throws Exception{
		
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(6.0);
		curvePoint.setValue(2.1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curvePoint);
		
		mockMvc
			.perform(put("/curvepoint")
					.param("id", "1")
					.with(user("max")
						.password("yeyo")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"curveid\":\"1\",\"term\":\"3\",\"value\":\"5\"}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void givenNotRegisteredCurvepoint1_whenPutCurvepointCalled_thenItShouldReturnStatusNotFound() throws Exception{
		
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(-1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curvePoint);
		
		mockMvc
			.perform(put("/curvepoint")
					.param("id", "1")
					.with(user("max")
						.password("yeyo")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"curveid\":\"1\",\"term\":\"3\",\"value\":\"5\"}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			;
		
	}
	
	@Test
	public void givenMissingRequestBody_whenPutCurvepointCalled_thenItShouldReturnStatusBadRequest() throws Exception{
		
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(-1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curvePoint);
		
		mockMvc
			.perform(put("/curvepoint")
					.param("id", "1")
					.with(user("max")
						.password("yeyo")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			;
		
	}

	
}
