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

import com.auth2.oseidclient.bid.service.FindBidByAccountService;
import com.auth2.oseidclient.entity.Bid;
import com.auth2.oseidclient.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private BidService bidService;

	@Nested
	@DisplayName("findBidByAccount")
	class FindBidByAccount {
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

			List<Bid> result = bidService.findBidByAccount("max");

			assertThat(result).isEqualTo(expectedBidList);
		}

		@Test
		public void givenANotRegisteredBidAccount_whenGetBidByAccountCalled_thenAListWithDefaultNotRegisteredBid() {

			List<Bid> expectedBidList = new ArrayList<>();

			Bid bid = new Bid("Not_Registered");
			expectedBidList.add(bid);

			when(bidRepository.findAll()).thenReturn(expectedBidList);

			List<Bid> result = bidService.findBidByAccount("LAX");

			assertThat(result).isEqualTo(expectedBidList);
		}
	}
	
	@Nested
	@DisplayName("findBidById")
	class FindBidById{
		
		@Test
		public void givenARegisteredBidId_whenFindByIdCalled_thenItShouldReturnTheExpectedBid() {
			Bid expectedBid = new Bid();
			expectedBid.setBidListId(1);
			
			List<Bid> expectedBidList = new ArrayList<>();
			expectedBidList.add(expectedBid);
			
			when(bidRepository.findAll()).thenReturn(expectedBidList);
		
			Bid result = bidService.findBidById(1);
			
			assertThat(result).isEqualTo(expectedBid);
		}
		
		@Test
		public void givenANotRegisteredBid_whenFindByIdCalled_thenItShouldReturnDefaultNotRegisteredBid() {
			Bid expectedBid = new Bid();
			expectedBid.setBidListId(-1);
			
			List<Bid> expectedBidList = new ArrayList<>();
			expectedBidList.add(expectedBid);
			when(bidRepository.findAll()).thenReturn(expectedBidList);
		
			Bid result = bidService.findBidById(2);
			
			assertThat(result).isEqualTo(expectedBid);
		}
		
	}
	
	@Nested
	@DisplayName("DeleteBid")
	class DeleteBid{
		@Test
		public void givenABid_whenDeleteBid_thenBidRepositoryDeleteMethodShouldBeUsedOnce() {
			
			Bid bid = new Bid();
			bid.setBidListId(1);
			
			bidService.deleteBid(bid);
			
			verify(bidRepository, times(1)).delete(bid);
			
		}
	}
	
	@Nested
	@DisplayName("AddBid")
	class AddBid{
		
		@Test
		public void givenABid_whenSaveBidCalled_thenRepositorySaveMethodShouldBeCalledOnce() {
			
			Bid bid = new Bid();
			bid.setAccount("mate");
			
			Bid bidWithId = new Bid();
			bidWithId.setBidListId(1);
			bid.setAccount("mate");
			
			when(bidRepository.saveAndFlush(bid)).thenReturn(bidWithId);
					
			Integer resultBid = bidService.saveBid(bid);
			
			assertThat(resultBid).isEqualTo(bidWithId.getBidListId());
		}
		
	}
}
