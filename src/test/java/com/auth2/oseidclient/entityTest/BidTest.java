package com.auth2.oseidclient.entityTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.auth2.oseidclient.bid.entity.Bid;

public class BidTest {

	@Test
	public void givenReferenceBid_whenClonedAndTheCopyIsModified_thenReferenceAndCopyAreDifferent() {
		
		Bid reference = new Bid("hi");
		reference.setBidListId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setBidListDate(ts);
		reference.setRevisionDate(ts2);

		Bid copy = (Bid) reference.clone();
		
		copy.setBidListId(2);

		assertThat(copy).isNotEqualTo(reference);
		
		
	}
	
	@Test
	public void givenReferenceBid_whenGetCreationDateAndTheResultIsModified_thenIfWeGetAgainBothAreDifferent() {
		
		Bid reference = new Bid();
		reference.setBidListId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setBidListDate(ts);
		reference.setRevisionDate(ts2);

		Timestamp firstGetModified = reference.getCreationDate();
		firstGetModified.setTime(System.currentTimeMillis()+500000);
		
		Timestamp secondGetReference = reference.getCreationDate();
		
		assertThat(firstGetModified).isNotEqualTo(secondGetReference);
		
		
	}
	
	@Test
	public void givenReferenceBid_whenGetBidListDateAndTheResultIsModified_thenIfWeGetAgainBothAreDifferent() {
		
		Bid reference = new Bid();
		reference.setBidListId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setBidListDate(ts);
		reference.setRevisionDate(ts2);

		Timestamp firstGetModified = reference.getBidListDate();
		firstGetModified.setTime(System.currentTimeMillis()+500000);
		
		Timestamp secondGetReference = reference.getBidListDate();
		
		assertThat(firstGetModified).isNotEqualTo(secondGetReference);
		
		
	}
	
	@Test
	public void givenReferenceBid_whenGetRevisionDateAndTheResultIsModified_thenIfWeGetAgainBothAreDifferent() {
		
		Bid reference = new Bid();
		reference.setBidListId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setBidListDate(ts);
		reference.setRevisionDate(ts2);

		Timestamp firstGetModified = reference.getRevisionDate();
		firstGetModified.setTime(System.currentTimeMillis()+500000);
		
		Timestamp secondGetReference = reference.getRevisionDate();
		
		assertThat(firstGetModified).isNotEqualTo(secondGetReference);
		
		
	}
	
	/*	@Test
	public void givenReference_whenGetAttributesCalledAndModified_thenReferenceAttributesAndModifiedAttributesShouldBeDifferents() {
		
		OseidUserDetails reference = new OseidUserDetails();
		reference.setEmail("mmm");
		
		HashMap<String, Object> att = new HashMap<>();
		att.put("email", "mmm");
		att.put("name", "lol");
		att.put("username", "zoll");
		
		Map<String, Object> referenceAttToModify = reference.getAttributes();
		
		referenceAttToModify.replace("email", "fill");
		referenceAttToModify.replace("name", "zoll");
		referenceAttToModify.replace("username", "bright");
		
		assertThat(referenceAttToModify).isNotEqualTo(reference.getAttributes());
		
	}
	
	@Test
	public void givenReferenceUser_whenCloned_thenRefenceEqualsCopyShouldBeTrue() {
		
		OseidUserDetails reference = new OseidUserDetails();
		reference.setEmail("mmm");
		
		HashMap<String, Object> att = new HashMap<>();
		att.put("email", "mmm");
		att.put("name", "lol");
		att.put("username", "zoll");
		
		reference.setAttributes(att);
		
		OseidUserDetails copy = (OseidUserDetails) reference.clone();

		Boolean result = reference.equals(copy);
		
		assertTrue(result);
		
	}
	*/
	
}
