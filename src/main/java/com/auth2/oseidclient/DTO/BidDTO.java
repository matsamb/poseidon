package com.auth2.oseidclient.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidDTO implements Cloneable{

	private Integer bidListId;
	@NotNull
	private String account;
	@NotNull
	private String type;
	@NotNull
	private Double bidQuantity;
	
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
