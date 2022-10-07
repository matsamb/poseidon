package com.auth2.oseidclient.entity;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Trade implements Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer tradeId;
	String account;
	String type;
	Double buyQuantity;
	Double sellQuantity;
	Double buyPrice;
	Double sellPrice;
	String benchmark;
	Timestamp tradeDate;
	String security;
	String status;
	String trader;
	String book;
	String creationName;
	Timestamp creationDate;
	String revisionName;
	Timestamp revisionDate;
	String dealName;
	String dealType;
	String sourceListId;
	String side;
	
	public Trade(String account) {
	this.account = account;
}

public void setTradeDate(Timestamp ts) {
	this.tradeDate = (Timestamp) ts.clone();
}

public Timestamp getTradeDate() {
	if (tradeDate != null) {
		return (Timestamp) tradeDate.clone();
	} else {
		return null;
	}
}

public void setCreationDate(Timestamp ts) {
	this.creationDate = (Timestamp) ts.clone();
}

public Timestamp getCreationDate() {
	if (creationDate != null) {
		return (Timestamp) creationDate.clone();
	} else {
		return null;
	}
}

public void setRevisionDate(Timestamp ts) {
	this.revisionDate = (Timestamp) ts.clone();
}

public Timestamp getRevisionDate() {
	if (revisionDate != null) {
		return (Timestamp) revisionDate.clone();
	} else {
		return null;

	}
}

public Object clone() {

	Trade result = null;
 
	try {
		result = (Trade) super.clone();
	} catch (CloneNotSupportedException c) {
		c.printStackTrace();
	}

	if (tradeDate != null) {
		result.tradeDate = (Timestamp) tradeDate.clone();
	}

	if (creationDate != null) {
		result.creationDate = (Timestamp) creationDate.clone();
	}

	if (revisionDate != null) {
		result.revisionDate = (Timestamp) revisionDate.clone();
	}

	return result;
}
	
}
