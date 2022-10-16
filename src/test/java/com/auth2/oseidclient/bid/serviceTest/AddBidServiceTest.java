package com.auth2.oseidclient.bid.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.bid.service.AddBidService;
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
public class AddBidServiceTest {

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private AddBidService addBidService;

	@Test
	public void givenABid_whenSaveBidCalled_thenRepositorySaveMethodShouldBeCalledOnce() {
		
		Bid bid = new Bid();
		bid.setAccount("mate");
		
		Bid bidWithId = new Bid();
		bidWithId.setBidListId(1);
		bid.setAccount("mate");
		
		when(bidRepository.saveAndFlush(bid)).thenReturn(bidWithId);
				
		Integer resultBid = addBidService.saveBid(bid);
		
		assertThat(resultBid).isEqualTo(bidWithId.getBidListId());
	}
	
}
