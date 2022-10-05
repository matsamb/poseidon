package com.auth2.oseidclient.bid.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.bid.entity.Bid;
import com.auth2.oseidclient.bid.repository.BidRepository;
import com.auth2.oseidclient.bid.service.DeleteBidService;

@ExtendWith(MockitoExtension.class)
public class DeleteBidServiceTest {

	@Mock
	private BidRepository bidRepository;
	
	@InjectMocks
	private DeleteBidService deleteBidService;
	
	@Test
	public void test() {
		
		Bid bid = new Bid();
		bid.setBidListId(1);
		
		deleteBidService.deleteBid(bid);
		
		verify(bidRepository, times(1)).delete(bid);
		
	}
	
}
