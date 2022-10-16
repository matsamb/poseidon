package com.auth2.oseidclient.oseidrating.restcontroller;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.FindOseidRatingByIdService;

@SpringBootTest
@AutoConfigureMockMvc
public class GetOseidRatingRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindOseidRatingByIdService findOseidRatingByIdService;
	
	@BeforeEach
	public void steUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(get("/rating"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenARegisteredRating_whenGetOseidRating_thenItShouldReturnStatusIsOk() throws Exception{
		
		OseidRating rating = new OseidRating();
		rating.setId(1);
		rating.setMoodysRating("mate");
		
		when(findOseidRatingByIdService.findOseidRatingById(1)).thenReturn(rating);
		
		mockMvc
			.perform(get("/rating")
					.param("id", "1")
					.with(user("lax")
						.password("lax")
						.roles("ADMIN")))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void givenANotRegisteredRating_whenGetOseidRating_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		OseidRating rating = new OseidRating();
		rating.setId(-1);
		rating.setMoodysRating("mate");
		
		when(findOseidRatingByIdService.findOseidRatingById(1)).thenReturn(rating);
		
		mockMvc
			.perform(get("/rating")
					.param("id", "1")
					.with(user("lax")
						.password("lax")
						.roles("ADMIN")))
			.andExpect(status().isNotFound())
			;
		
	}
}
