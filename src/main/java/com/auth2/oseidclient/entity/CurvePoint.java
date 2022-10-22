package com.auth2.oseidclient.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "curve_point")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer curveId;
	@Getter(value = AccessLevel.NONE)
	private Timestamp asOfDate;
	private Double term;
	private Double value;
	@Getter(value = AccessLevel.NONE)
	private Timestamp creationDate;
	
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
	
	public void setAsOfDate(Timestamp ts) {
		this.asOfDate = (Timestamp) ts.clone();
	}

	public Timestamp getRevisionDate() {
		if (asOfDate != null) {
			return (Timestamp) asOfDate.clone();
		} else {
			return null;

		}
	}
	
}
