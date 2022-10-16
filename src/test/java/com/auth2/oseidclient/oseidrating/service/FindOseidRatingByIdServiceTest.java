package com.auth2.oseidclient.oseidrating.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@ExtendWith(MockitoExtension.class)
public class FindOseidRatingByIdServiceTest {

	@Mock
	private OseidRatingRepository oseidRatingRepository;
	
	@InjectMocks
	private FindOseidRatingByIdService findOseidRatingByIdService;
	
	@Test
	public void givenARegisteredRating_whenFindRatingById_thenItshouldReturnTheRegisteredRating() {
		
		OseidRating registeredRating = new OseidRating();
		registeredRating.setId(1);
		
		List<OseidRating> ratingList = new ArrayList<>();
		ratingList.add(registeredRating);
		
		when(oseidRatingRepository.findAll()).thenReturn(ratingList);
		
		OseidRating ratingReturned = findOseidRatingByIdService.findOseidRatingById(1);
		
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
		
		OseidRating ratingReturned = findOseidRatingByIdService.findOseidRatingById(2);
		
		assertThat(ratingReturned).isEqualTo(notRegistered);
		
	}
	
}
