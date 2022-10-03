package com.auth2.oseidclient.bid.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth2.oseidclient.bid.entity.Bid;
import com.auth2.oseidclient.bid.repository.BidRepository;
import com.auth2.oseidclient.bid.service.FindBidByAccountService;

@ExtendWith(MockitoExtension.class)
public class FindBidByAccountServiceTest {

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private FindBidByAccountService findBidByAccountService;

	@Test
	public void givenAListOfTwoMaxAccountBids_whenGetBidByAccountMaxCalled_thenTheReturnedListShouldBeAqualsToTheGivenList() {

		List<Bid> expectedBidList = new ArrayList<>();
		
		Bid bid = new Bid("max");
		bid.setDealName("bId");
		Bid bod = new Bid("max");
		bod.setDealName("bOd");
		expectedBidList.add(bid);
		expectedBidList.add(bod);

		when(bidRepository.findAll()).thenReturn(expectedBidList);

		List<Bid> result = findBidByAccountService.findBidByAccount("max");

		assertThat(result).isEqualTo(expectedBidList);
	}

	@Test
	public void givenANotRegisteredBidAccount_whenGetBidByAccountCalled_thenAListWithDefaultNotRegisteredBid() {

		List<Bid> expectedBidList = new ArrayList<>();
		
		Bid bid = new Bid("Not_Registered");
		expectedBidList.add(bid);

		when(bidRepository.findAll()).thenReturn(expectedBidList);

		List<Bid> result = findBidByAccountService.findBidByAccount("LAX");

		assertThat(result).isEqualTo(expectedBidList);
	}
}
