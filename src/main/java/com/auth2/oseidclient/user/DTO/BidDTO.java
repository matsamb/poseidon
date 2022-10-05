package com.auth2.oseidclient.user.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidDTO implements Cloneable{

	Integer bidListId;
	@NotNull
	String account;
	@NotNull
	String type;
	@NotNull
	Double bidQuantity;
	
	public Object clone() {
		BidDTO copy = null;
		
		try {
			copy = (BidDTO) super.clone();
		}catch(CloneNotSupportedException c) {
			c.printStackTrace();
		}
		
		return copy;
	}

}
