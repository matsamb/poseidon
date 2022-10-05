package com.auth2.oseidclient.entityTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.auth2.oseidclient.entity.Trade;

public class TradeTest {
	@Test
	public void givenReferenceTrade_whenClonedAndTheCopyIsModified_thenReferenceAndCopyAreDifferent() {
		
		Trade reference = new Trade("hi");
		reference.setTradeId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setTradeDate(ts);
		reference.setRevisionDate(ts2);

		Trade copy = (Trade) reference.clone();
		
		copy.setTradeId(2);

		assertThat(copy).isNotEqualTo(reference);
		
		
	}
	
	@Test
	public void givenReferenceTrade_whenGetCreationDateAndTheResultIsModified_thenIfWeGetAgainBothAreDifferent() {
		
		Trade reference = new Trade();
		reference.setTradeId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setTradeDate(ts);
		reference.setRevisionDate(ts2);

		Timestamp firstGetModified = reference.getCreationDate();
		firstGetModified.setTime(System.currentTimeMillis()+500000);
		
		Timestamp secondGetReference = reference.getCreationDate();
		
		assertThat(firstGetModified).isNotEqualTo(secondGetReference);
		
		
	}
	
	@Test
	public void givenReferenceTrade_whenGetTradeDateAndTheResultIsModified_thenIfWeGetAgainBothAreDifferent() {
		
		Trade reference = new Trade();
		reference.setTradeId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setTradeDate(ts);
		reference.setRevisionDate(ts2);

		Timestamp firstGetModified = reference.getTradeDate();
		firstGetModified.setTime(System.currentTimeMillis()+500000);
		
		Timestamp secondGetReference = reference.getTradeDate();
		
		assertThat(firstGetModified).isNotEqualTo(secondGetReference);
		
		
	}
	
	@Test
	public void givenReferenceBid_whenGetRevisionDateAndTheResultIsModified_thenIfWeGetAgainBothAreDifferent() {
		
		Trade reference = new Trade();
		reference.setTradeId(1);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Timestamp ts2 = new Timestamp(System.currentTimeMillis()+1000);

		reference.setCreationDate(ts);
		reference.setTradeDate(ts);
		reference.setRevisionDate(ts2);

		Timestamp firstGetModified = reference.getRevisionDate();
		firstGetModified.setTime(System.currentTimeMillis()+500000);
		
		Timestamp secondGetReference = reference.getRevisionDate();
		
		assertThat(firstGetModified).isNotEqualTo(secondGetReference);
		
		
	}
	
}
