package com.auth2.oseidclient.curvepoint.restcontroller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.auth2.oseidclient.curvepoint.service.SaveCurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostCurvePointControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SaveCurvePointService saveCurvePointService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(post("/curvepoint"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
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
