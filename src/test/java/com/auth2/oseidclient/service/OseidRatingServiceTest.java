package com.auth2.oseidclient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@ExtendWith(MockitoExtension.class)
public class OseidRatingServiceTest {

	@Mock
	private OseidRatingRepository oseidRatingRepository;
	
	@InjectMocks
	private OseidRatingService oseidRatingService;
	
	@Nested
	@DisplayName("addOseidRating")
	class addOseidRating{
		
		@Test
		public void givenNoRegisteredRating_whenAddRating_thenGeneratedIdShouldBeOne() {
			
			OseidRating rating = new OseidRating();
			rating.setMoodysRating("2");
			
			OseidRating ratingWithId = new OseidRating();
			ratingWithId.setId(1);
			ratingWithId.setMoodysRating("2");
			
			when(oseidRatingRepository.saveAndFlush(rating)).thenReturn(ratingWithId);
			
			Integer ratingId = oseidRatingService.saveOseidRating(rating);
			
			assertThat(ratingId).isEqualTo(1);
			
		}
		
	}
	
	@Nested
	@DisplayName("findOseidRating")
	class findOseidRating{
		
		@Test
		public void givenARegisteredRating_whenFindRatingById_thenItshouldReturnTheRegisteredRating() {
			
			OseidRating registeredRating = new OseidRating();
			registeredRating.setId(1);
			
			List<OseidRating> ratingList = new ArrayList<>();
			ratingList.add(registeredRating);
			
			when(oseidRatingRepository.findAll()).thenReturn(ratingList);
			
			OseidRating ratingReturned = oseidRatingService.findOseidRatingById(1);
			
			assertThat(ratingReturned).isEqualTo(registeredRating);
			
		}
		
		@Test
		public void givenANotRegisteredRating_whenFindRatingById_thenItshouldReturnTheDefaultNotRegisteredRating() {
			
			OseidRating rating = new OseidRating();
			rating.setId(1);
			
			OseidRating notRegistered = new OseidRating();
			notRegistered.setId(-1);
			
			List<OseidRating> ratingList = new ArrayList<>();
			ratingList.add(rating);
			
			when(oseidRatingRepository.findAll()).thenReturn(ratingList);
			
			OseidRating ratingReturned = oseidRatingService.findOseidRatingById(2);
			
			assertThat(ratingReturned).isEqualTo(notRegistered);
			
		}
		
	}
	
	@Nested
	@DisplayName("deleteOseidRating")
	class deleteOseidRating{
		
		@Test
		public void whenDeleteRating_thenOseidRepositoryDeleteMethodShouldBeUsedOnce() {
			
			OseidRating rating = new OseidRating();
			rating.setId(1);
			
			oseidRatingService.deleteOseidRating(rating);
			
			verify(oseidRatingRepository, times(1)).delete(rating);
			
			
		}
	}
	
}
