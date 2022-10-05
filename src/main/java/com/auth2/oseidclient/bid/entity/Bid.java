package com.auth2.oseidclient.bid.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Bid implements Cloneable {

	@Id
	@Column(length = 10)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer bidListId;
	@Column(length = 10)
	String account;
	@Column(length = 10)
	String type;
	Double bidQuantity;
	Double askQuantity;
	@Column(length = 10)
	Double bid;
	@Column(length = 10)
	Double ask;
	@Column(length = 10)
	String benchmark;
	Timestamp bidListDate;
	@Column(length = 10)
	String commentary;
	@Column(length = 10)
	String security;
	@Column(length = 10)
	String status;
	@Column(length = 10)
	String trader;
	@Column(length = 10)
	String book;
	@Column(length = 10)
	String creationName;
	Timestamp creationDate;
	@Column(length = 10)
	String revisionName;
	Timestamp revisionDate;
	@Column(length = 10)
	String dealName;
	@Column(length = 10)
	String dealType;
	@Column(length = 10)
	String sourceListId;
	@Column(length = 10)
	String side;

	public Bid(String account) {
		this.account = account;
	}

	public void setBidListDate(Timestamp ts) {
		this.bidListDate = (Timestamp) ts.clone();
	}

	public Timestamp getBidListDate() {
		if (bidListDate != null) {
			return (Timestamp) bidListDate.clone();
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

		Bid result = null;

		try {
			result = (Bid) super.clone();
		} catch (CloneNotSupportedException c) {
			c.printStackTrace();
		}

		if (bidListDate != null) {
			result.bidListDate = (Timestamp) bidListDate.clone();
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
