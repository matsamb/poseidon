package com.auth2.oseidclient.bid.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.bid.service.FindBidByIdService;
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
public class FindBidByIdServiceTest {

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private FindBidByIdService findBidByIdService;
	
	@Test
	public void givenARegisteredBidId_whenFindByIdCalled_thenItShouldReturnTheExpectedBid() {
		Bid expectedBid = new Bid();
		expectedBid.setBidListId(1);
		
		List<Bid> expectedBidList = new ArrayList<>();
		expectedBidList.add(expectedBid);
		
		when(bidRepository.findAll()).thenReturn(expectedBidList);
	
		Bid result = findBidByIdService.findBidById(1);
		
		assertThat(result).isEqualTo(expectedBid);
	}
	
	@Test
	public void givenANotRegisteredBid_whenFindByIdCalled_thenItShouldReturnDefaultNotRegisteredBid() {
		Bid expectedBid = new Bid();
		expectedBid.setBidListId(-1);
		
		List<Bid> expectedBidList = new ArrayList<>();
		expectedBidList.add(expectedBid);
		when(bidRepository.findAll()).thenReturn(expectedBidList);
	
		Bid result = findBidByIdService.findBidById(2);
		
		assertThat(result).isEqualTo(expectedBid);
	}
	
}
