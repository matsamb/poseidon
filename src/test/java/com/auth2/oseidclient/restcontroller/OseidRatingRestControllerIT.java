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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.service.OseidRatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class OseidRatingRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OseidRatingService oseidRatingService;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.apply(springSecurity())
					.build()
					;
		
	}
	
	@Nested
	@DisplayName("getOseidRating")
	class getOseidRating{
		
		@Test
		public void givenARegisteredRating_whenGetOseidRating_thenItShouldReturnStatusIsOk() throws Exception{
			
			OseidRating rating = new OseidRating();
			rating.setId(1);
			rating.setMoodysRating("mate");
			
			when(oseidRatingService.findOseidRatingById(1)).thenReturn(rating);
			
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
			
			when(oseidRatingService.findOseidRatingById(1)).thenReturn(rating);
			
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
	
	@Nested
	@DisplayName("postOseidRating")
	class postOseidRating{
		
		@Test
		public void givenANewRating_whenPostRating_thenStatusCreatedShouldBeReturned() throws Exception{
			
			OseidRating ratingSaved = new OseidRating();
			ratingSaved.setMoodysRating("1");
			
			OseidRating ratingReturned = new OseidRating();
			ratingReturned.setId(1);
			ratingReturned.setMoodysRating("1");
			
			when(oseidRatingService.saveOseidRating(ratingSaved)).thenReturn(ratingReturned.getId());
			
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
	
	@Nested
	@DisplayName("putOseidRating")
	class putOseidRating{
		
		@Test
		public void givenARegisteredRating_whenPutOseidRating_thenItShouldReturnStatusIsOk() throws Exception{
			
			OseidRating rating = new OseidRating();
			rating.setId(1);
			
			when(oseidRatingService.findOseidRatingById(1)).thenReturn(rating);
			
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
			
			when(oseidRatingService.findOseidRatingById(1)).thenReturn(rating);
			
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
		
	}
	
	@Nested
	@DisplayName("deleteOseidRating")
	class deleteOseidRating{
		
		@Test
		public void whenDeleteOseidRating_thenReturnStatusIsOk() throws Exception{
			
			mockMvc
				.perform(delete("/rating")
						.param("id", "1")
						.with(user("nex")
							.password("mex")
							.roles("ADMIN")))
				.andExpect(status().isOk())
				;
			
		}
		
	}
	
}
