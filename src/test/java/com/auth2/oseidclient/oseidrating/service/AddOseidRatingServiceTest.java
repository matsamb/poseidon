package com.auth2.oseidclient.oseidrating.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@ExtendWith(MockitoExtension.class)
public class AddOseidRatingServiceTest {

	@Mock
	private OseidRatingRepository oseidRatingRepository;
	
	@InjectMocks
	private AddOseidRatingService addOseidRatingService;
	
	@Test
	public void test() {
		
		OseidRating rating = new OseidRating();
		rating.setMoodysRating("2");
		
		OseidRating ratingWithId = new OseidRating();
		ratingWithId.setId(1);
		ratingWithId.setMoodysRating("2");
		
		when(oseidRatingRepository.saveAndFlush(rating)).thenReturn(ratingWithId);
		
		Integer ratingId = addOseidRatingService.saveOseidRating(rating);
		
		assertThat(ratingId).isEqualTo(1);
		
	}
	
}
