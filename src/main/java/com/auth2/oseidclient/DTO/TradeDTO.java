package com.auth2.oseidclient.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeDTO implements Cloneable{

	private Integer tradeId;
	@NotNull
	private String account;
	@NotNull
	private String type;
	
	public Object clone() {
		TradeDTO copy = null;
		
		try {
			copy = (TradeDTO) super.clone();
		}catch(CloneNotSupportedException c) {
			c.printStackTrace();
		}
		
		return copy;
	}
	
}
