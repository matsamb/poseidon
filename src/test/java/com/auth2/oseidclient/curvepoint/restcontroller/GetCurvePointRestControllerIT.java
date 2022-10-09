/**
 * 
 */
package com.auth2.oseidclient.curvepoint.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.curvepoint.service.FindCurvePointByIdService;
import com.auth2.oseidclient.entity.CurvePoint;

/**
 * @author matlu
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
public class GetCurvePointRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindCurvePointByIdService findCurvePointByIdService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/curvepoint"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenRegisteredCurvePoint1_whenGetCurvePointCalled_thenItShouldReturnStatusOk() throws Exception {
		
		CurvePoint curve = new CurvePoint();
		curve.setId(1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curve);
		
		mockMvc
			.perform(get("/curvepoint")
					.param("id", "1")
					.with(user("max")
						.password("fleax")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
			;
	}
	
	@Test
	public void givenANotRegisteredCurvePoint_whenGetCalled_ItShouldReturnStatusOk() throws Exception {
		
		CurvePoint curve = new CurvePoint();
		curve.setId(-1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curve);
		
		mockMvc
			.perform(get("/curvepoint")
					.param("id", "1")
					.with(user("max")
						.password("fleax")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			;
	}
	
	@Test
	public void givenANotRegisteredCurvePoint_whenGetCalled_ItShouldReturnNothingWithStatusOk() throws Exception {
		
		CurvePoint curve = new CurvePoint();
		curve.setId(-1);
		
		when(findCurvePointByIdService.findCurvePointById(1)).thenReturn(curve);
		
		mockMvc
			.perform(get("/curvepoint")
					.param("id", "1")
					.with(user("max")
						.password("fleax")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
			;
	}
	
	
}
