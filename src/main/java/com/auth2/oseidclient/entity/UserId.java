package com.auth2.oseidclient.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable, Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	public Object clone() {
		UserId copy = null;
		
		try {
			copy = (UserId) super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return copy;
	}
	
}
