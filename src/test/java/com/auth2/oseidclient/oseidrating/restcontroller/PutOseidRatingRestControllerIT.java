package com.auth2.oseidclient.oseidrating.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.AddOseidRatingService;
import com.auth2.oseidclient.oseidrating.service.FindOseidRatingByIdService;

@SpringBootTest
@AutoConfigureMockMvc
public class PutOseidRatingRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindOseidRatingByIdService findOseidRatingByIdService;
	
	@MockBean
	private AddOseidRatingService addOseidRatingService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(put("/rating"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenARegisteredRating_whenPutOseidRating_thenItShouldReturnStatusIsOk() throws Exception{
		
		OseidRating rating = new OseidRating();
		rating.setId(1);
		
		when(findOseidRatingByIdService.findOseidRatingById(1)).thenReturn(rating);
		
		mockMvc
			.perform(put("/rating")
					.param("id", "1")
					.with(user("mass")
						.password("lass")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"id\": 1,\"moodysRating\": 2,\"sandPRating\": 3,\"fitchRating\": 4,\"orderNumber\": 5}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			;
		
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPutRating_thenItShouldReturnStatusIsBadRequest() throws Exception{
		
		mockMvc
		.perform(put("/rating")
				.param("id", "1")
				.with(user("mass")
					.password("lass")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		;
		
	}

	@Test
	public void givenANotRegisteredOseidRating_whenPutOseidRating_thenItShouldReturnStatusIsNotFound() throws Exception{
		
		OseidRating rating = new OseidRating();
		rating.setId(-1);
		
		when(findOseidRatingByIdService.findOseidRatingById(1)).thenReturn(rating);
		
		mockMvc
		.perform(put("/rating")
				.param("id", "1")
				.with(user("mass")
					.password("lass")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1,\"moodysRating\":2,\"sandPRating\": 3,\"fitchRating\": 4,\"orderNumber\": 5}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		;
		
	}
	
/*	@Test
	public void givenAMissingRequestedMoodysratingField_whenPutOseidRating_thenItShouldReturnStatusIsBadRequest() throws Exception{
		
		OseidRating rating = new OseidRating();
		rating.setId(-1);
		
		when(findOseidRatingByIdService.findOseidRatingById(1)).thenReturn(rating);
		
		mockMvc
		.perform(put("/rating")
				.param("id", "1")
				.with(user("mass")
					.password("lass")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1,\"moodysRating\":\"\",\"sandPRating\": 3,\"fitchRating\": 4,\"orderNumber\": 5}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		;
		
	}*/

	
}
