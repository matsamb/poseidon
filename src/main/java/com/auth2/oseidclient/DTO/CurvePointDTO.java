package com.auth2.oseidclient.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurvePointDTO implements Cloneable{

	
	private Integer id;
	@JsonProperty("curveid")
	private Integer curveId;
	private Double term;
	private Double value;
	
	public Object clone() {
		
		CurvePointDTO copy = null;
		
		try {
			copy = (CurvePointDTO)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
	
}
