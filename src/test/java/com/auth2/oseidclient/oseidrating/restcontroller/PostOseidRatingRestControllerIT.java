package com.auth2.oseidclient.oseidrating.restcontroller;

import static org.mockito.Mockito.when;
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

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.oseidrating.service.AddOseidRatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class PostOseidRatingRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AddOseidRatingService addOseidRatingService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.defaultRequest(post("/rating"))
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Test
	public void givenANewRating_whenPostRating_thenStatusCreatedShouldBeReturned() throws Exception{
		
		OseidRating ratingSaved = new OseidRating();
		ratingSaved.setMoodysRating("1");
		
		OseidRating ratingReturned = new OseidRating();
		ratingReturned.setId(1);
		ratingReturned.setMoodysRating("1");
		
		when(addOseidRatingService.saveOseidRating(ratingSaved)).thenReturn(ratingReturned.getId());
		
		mockMvc
			.perform(post("/rating")
					.with(user("tex")
						.password("les")
						.roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"id\": 1,\"moodysRating\": 2,\"sandPRating\": 3,\"fitchRating\": 4,\"orderNumber\": 5}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			;
					
	}
	
	@Test
	public void givenAnEmptyRequestBody_whenPostRating_thenStatusBadRequestShouldBeReturned() throws Exception{
		
		mockMvc
		.perform(post("/rating")
				.with(user("tex")
					.password("les")
					.roles("ADMIN")))
		.andExpect(status().isBadRequest())
		;
		
	}
	
	@Test
	public void givenANewRuleMissingMoodysratingConstraint_whenPostRuleIsCalled_thenStatusCreatedShouldBeReturned() throws Exception{
		
		mockMvc
		.perform(post("/rating")
				.with(user("tex")
					.password("les")
					.roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1,\"moodysRating\":,\"sandPRating\": 3,\"fitchRating\": 4,\"orderNumber\": 5}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		;
		
	}


	
}
