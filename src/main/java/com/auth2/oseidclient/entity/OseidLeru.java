package com.auth2.oseidclient.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OseidLeru implements Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@NotBlank
	String name;
	String description;
	String json;
	String template;
	String sqlStr;
	String sqlPart;

	public Object clone() {
		OseidLeru copy = null;
		try {
			copy = (OseidLeru)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
}
