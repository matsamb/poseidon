package com.auth2.oseidclient.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.CurvePoint;
import com.auth2.oseidclient.service.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CurvePointService curvePointService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Nested
	@DisplayName("getCurvePoint")
	class getCurvePoint{
		
		@Test
		public void givenRegisteredCurvePoint1_whenGetCurvePointCalled_thenItShouldReturnStatusOk() throws Exception {
			
			CurvePoint curve = new CurvePoint();
			curve.setId(1);
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curve);
			
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
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curve);
			
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
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curve);
			
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
	
	@Nested
	@DisplayName("postCurvePoint")
	class postCurvePoint{
		
		@Test
		public void givenANewCurvepoint_whenPostCurvepointIsCalled_thenItShouldReturnStatusIsCreated() throws Exception {
			
			mockMvc
				.perform(post("/curvepoint")
						.with(user("max")
							.password("max")
							.roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"curveid\":\"1\",\"term\":\"3\",\"value\":\"5\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				;
			
		}
		
		@Test
		public void givenNoRequestBody_whenPostCurvepointIsCalled_thenItShouldReturnStatusIsBadRequest() throws Exception {
			
			mockMvc
				.perform(post("/curvepoint")
						.with(user("max")
							.password("max")
							.roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				;
			
		}
		
		@Test
		public void givenWrongRequestBodyInputType_whenPostCurvepointIsCalled_thenItShouldReturnStatusIsBadRequest() throws Exception {
			
			mockMvc
				.perform(post("/curvepoint")
						.with(user("max")
							.password("max")
							.roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"curveid\":\"1\",\"term\":\"kl\",\"value\":\"5\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				;
			
		}
		
	}
	
	@Nested
	@DisplayName("putCurvePoint")
	class putCurvePoint{
		
		@Test
		public void givenRegisteredCurvepoint1_whenPutCurvepointCalled_thenItShouldReturnStatusIsOk() throws Exception{
			
			CurvePoint curvePoint = new CurvePoint();
			curvePoint.setId(1);
			curvePoint.setCurveId(2);
			curvePoint.setTerm(6.0);
			curvePoint.setValue(2.1);
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curvePoint);
			
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
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curvePoint);
			
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
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curvePoint);
			
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
	
	@Nested
	@DisplayName("deleteCurvePoint")
	class deleteCurvePoint{
		
		@Test
		public void givenRegisteredCurvePoint1_whenDeleteCalled_thenItShouldReturnStatusIsOk() throws Exception{
			
			CurvePoint curveToDelete = new CurvePoint();
			curveToDelete.setId(1);
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curveToDelete);
			
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
		public void givenNotRegisteredCurvePoint1_whenDeleteCalled_thenItShouldReturnStatusNotFound() throws Exception{
			
			CurvePoint curveToDelete = new CurvePoint();
			curveToDelete.setId(-1);
			
			when(curvePointService.findCurvePointById(1)).thenReturn(curveToDelete);
			
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
	
}
