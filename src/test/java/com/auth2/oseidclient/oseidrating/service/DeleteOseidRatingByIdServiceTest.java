package com.auth2.oseidclient.oseidrating.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.entity.OseidRating;
import com.auth2.oseidclient.repository.OseidRatingRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteOseidRatingByIdServiceTest {

	@Mock
	private OseidRatingRepository oseidRatingRepository;
	
	@InjectMocks
	private DeleteOseidRatingService deleteOseidRatingService;
	
	@Test
	public void test() {
		
		OseidRating rating = new OseidRating();
		rating.setId(1);
		
		deleteOseidRatingService.deleteOseidRating(rating);
		
		verify(oseidRatingRepository, times(1)).delete(rating);
		
		
	}
	
	
}
