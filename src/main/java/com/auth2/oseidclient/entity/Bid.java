package com.auth2.oseidclient.entity;

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
	private Integer bidListId;
	@Column(length = 10)
	private String account;
	@Column(length = 10)
	private String type;
	private Double bidQuantity;
	private Double askQuantity;
	@Column(length = 10)
	private Double bid;
	@Column(length = 10)
	private Double ask;
	@Column(length = 10)
	private String benchmark;
	private Timestamp bidListDate;
	@Column(length = 10)
	private String commentary;
	@Column(length = 10)
	private String security;
	@Column(length = 10)
	private String status;
	@Column(length = 10)
	private String trader;
	@Column(length = 10)
	private String book;
	@Column(length = 10)
	private String creationName;
	private Timestamp creationDate;
	@Column(length = 10)
	private String revisionName;
	private Timestamp revisionDate;
	@Column(length = 10)
	private String dealName;
	@Column(length = 10)
	private String dealType;
	@Column(length = 10)
	private String sourceListId;
	@Column(length = 10)
	private String side;

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
