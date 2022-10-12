package com.auth2.oseidclient.curvepoint.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.curvepoint.service.DeleteCurvePointService;
import com.auth2.oseidclient.curvepoint.service.FindCurvePointByIdService;
import com.auth2.oseidclient.entity.CurvePoint;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteCurvePointRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DeleteCurvePointService deleteCurvePointService;
	
	@MockBean
	private FindCurvePointByIdService findCurvePointByIdService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(delete("/curvepoint"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void test80/*givenRegisteredCurvePoint1_whenDeleteCalled_thenItShouldReturnStatusIsOk*/() throws Exception{
		
		CurvePoint curveToDelete = new CurvePoint();
		curveToDelete.setId(1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curveToDelete);
		
		mockMvc
			.perform(delete("/curvepoint")
					.param("id", "1")
					.with(user("masa")
						.password("sasa")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void test90/*givenNotRegisteredCurvePoint1_whenDeleteCalled_thenItShouldReturnStatusNotFound*/() throws Exception{
		
		CurvePoint curveToDelete = new CurvePoint();
		curveToDelete.setId(-1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curveToDelete);
		
		mockMvc
			.perform(delete("/curvepoint")
					.param("id", "1")
					.with(user("masa")
						.password("sasa")
						.roles("ADMIN")))
			.andExpect(status().isNotFound())
			;
		
	}
 
	
}
